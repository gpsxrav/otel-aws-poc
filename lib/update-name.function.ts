import { Context, APIGatewayProxyResult, APIGatewayEvent } from 'aws-lambda';
import { DynamoDBDocumentClient, GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import axios from 'axios';

// Must use require as we are using the `opentelemetry` provided by the layer
//  Require causes 'any', we can add the types back via import type
const { context, propagation, trace, SpanStatusCode } = require('@opentelemetry/api')
import type { Span, SpanOptions } from '@opentelemetry/api'
import { CodeDeployServerDeployAction } from 'aws-cdk-lib/aws-codepipeline-actions';

const tracer = trace.getTracer('my-service-tracer')

const ddbClient = new DynamoDBClient({});
const ddbDocClient = DynamoDBDocumentClient.from(ddbClient);

// Acts like a decorator, helps reduce nesting that comes from 'startActiveSpan'
function spanify<F extends (args: Parameters<F>[0]) => ReturnType<F>>(
  fn: F,
  spanOptions: SpanOptions = {},
  spanName: string = fn.name.replace(/^_/, '')
): (args: Omit<Parameters<F>[0], 'span'>) => Promise<ReturnType<F>> {
  return (functionArgs) => {
    return tracer.startActiveSpan(spanName, spanOptions, async (span: Span): Promise<ReturnType<F>> => {
      try {
        const result = await fn({ ...functionArgs, span })

        span.setStatus({
          code: SpanStatusCode.OK,
        })

        return result
      } catch (error: any) {
        const errorMessage = String(error)

        if (!error.spanExceptionRecorded) {
          span.recordException(error)
          span.setStatus({ code: SpanStatusCode.ERROR, message: errorMessage })
          error.spanExceptionRecorded = true
        }

        throw error
      } finally {
        span.end()
      }
    })
  }
}



const updateName = spanify(async function updateName({ name , span }: { name: string, span: Span }): Promise<number> {

  span.setAttributes({ name })

  let id = getRandomInt(10000);
  await ddbDocClient.send(new PutCommand({
    TableName: process.env.DDB_TABLE_NAME,
    Item: {
      id: id,
      name: name
    }
  }));

  return id
})

function getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
}

const handler = async (event: APIGatewayEvent, reqContext: Context): Promise<APIGatewayProxyResult> => {
  console.log(`Event: ${JSON.stringify(event, null, 2)}`);
  console.log(`Context: ${JSON.stringify(reqContext, null, 2)}`);

  let eventJson = JSON.parse(JSON.stringify(event, null, 2));
  let reqContextJson = JSON.parse(JSON.stringify(reqContext, null, 2));

  let activeContext = propagation.extract(context.active(), reqContextJson.headers);
  console.log(activeContext);
    return tracer.startActiveSpan('handler', { root: false }, activeContext, async (span: any) => {
    trace.setSpan(activeContext, span);
    if (!eventJson.name) {
        return {
            statusCode: 500,
            body: JSON.stringify({
              'msg': 'Invalid request'
            }),
          }; 
      } 
  

    let name: string = eventJson.name;

    const newId = await updateName({ name })

    const response = {
      statusCode: 200,
      body: JSON.stringify({
        'msg': 'name update in db with ID - '+ newId,
      }),
    };

    span.end()
    return response
  })
};

// Must export via handler, required due to otel manipulating lambda entry point
//  https://github.com/open-telemetry/opentelemetry-js/issues/1946
module.exports = { handler }
