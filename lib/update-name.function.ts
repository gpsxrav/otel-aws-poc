import { Context, APIGatewayProxyResult, APIGatewayEvent } from 'aws-lambda';
import { DynamoDBDocumentClient, GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import axios from 'axios';

// Must use require as we are using the `opentelemetry` provided by the layer
//  Require causes 'any', we can add the types back via import type
const { context, propagation, trace, SpanStatusCode } = require('@opentelemetry/api')
import { Span, SpanOptions } from '@opentelemetry/api'
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

async function updateName(name: string): Promise<number> {

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

const handler = async (event: APIGatewayEvent, context: Context): Promise<APIGatewayProxyResult> => {
  let span: Span;
  console.log(`Event: ${JSON.stringify(event, null, 2)}`);
  console.log(`Context: ${JSON.stringify(context, null, 2)}`);

  let eventJson = JSON.parse(JSON.stringify(event, null, 2));
  let reqContextJson = JSON.parse(JSON.stringify(context, null, 2));

  let activeSpan: Span = trace.getActiveSpan()
  let activeSpanCtx = activeSpan.spanContext()
  console.log(activeSpan)
  console.log(activeSpanCtx)
  

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
    const newId = await updateName(name)

    const response = {
      statusCode: 200,
      body: JSON.stringify({
        'msg': 'name update in db with ID - '+ newId,
      }),
    };
    
    return response

};


module.exports = { handler }
