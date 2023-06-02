import { Context, APIGatewayProxyResult, APIGatewayEvent } from 'aws-lambda';
import { DynamoDBDocumentClient, GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import axios from 'axios';

// Must use require as we are using the `opentelemetry` provided by the layer
//  Require causes 'any', we can add the types back via import type
const { context, propagation, trace, SpanStatusCode } = require('@opentelemetry/api')
import type { Span, SpanOptions } from '@opentelemetry/api'
import { CodeDeployServerDeployAction } from 'aws-cdk-lib/aws-codepipeline-actions';
import {W3CTraceContextPropagator} from "@opentelemetry/core";
import {
    defaultTextMapGetter,
    defaultTextMapSetter,
    ROOT_CONTEXT,
} from "@opentelemetry/api";

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



async function updateName(name: string, span: Span): Promise<number> {

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
}

function getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
}

const handler = async (event: APIGatewayEvent, reqContext: Context): Promise<APIGatewayProxyResult> => {
  let span: Span;
  console.log(`Event: ${JSON.stringify(event, null, 2)}`);
  console.log(`Context: ${JSON.stringify(reqContext, null, 2)}`);

  let eventJson = JSON.parse(JSON.stringify(event, null, 2));
  let reqContextJson = JSON.parse(JSON.stringify(reqContext, null, 2));
  const propagator = new W3CTraceContextPropagator();

  const parentCtx = propagator.extract(ROOT_CONTEXT, event.headers.carrier, defaultTextMapGetter);

  if (!event.headers.traceparent) {
    span = tracer.startActiveSpan('update-name-api', { root: true });
  }else {
    span = tracer.startSpan("update-name-api", undefined, parentCtx);
  }

  console.log('parentCtx - '+parentCtx)
    if (!eventJson.body) {
        return {
            statusCode: 500,
            body: JSON.stringify({
              'msg': 'Invalid request'
            }),
          }; 
      } 
  

    let body: any = JSON.parse(eventJson.body);
    console.log('body - '+ JSON.stringify(body))
    let name = body.name;
    console.log('name - '+ name)
    const newId = await updateName(name, span)

    const response = {
      statusCode: 200,
      body: JSON.stringify({
        'msg': 'name update in db with ID - '+ newId,
      }),
    };

    span.end()
    return response

};

// Must export via handler, required due to otel manipulating lambda entry point
//  https://github.com/open-telemetry/opentelemetry-js/issues/1946
module.exports = { handler }
