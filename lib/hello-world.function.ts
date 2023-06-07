import { Context, APIGatewayProxyResult, APIGatewayEvent } from 'aws-lambda';
import { DynamoDBDocumentClient, GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import axios from 'axios';
import {W3CTraceContextPropagator} from "@opentelemetry/core";
import {
    defaultTextMapGetter,
    defaultTextMapSetter,
    ROOT_CONTEXT,
} from "@opentelemetry/api";

const { trace, SpanStatusCode } = require('@opentelemetry/api')
import type { Span, SpanOptions } from '@opentelemetry/api'

const tracer = trace.getTracer('my-service-tracer')

const makeAPIcall = async function makeAPIcall(myname: string): Promise<string> {

  let endpoint = "https://ldky8xfdil.execute-api.us-west-2.amazonaws.com/prod/";
  let request = {
    name: myname
  }

  let response = await invoke(request, endpoint);
  console.log(response);
  return myname
}


const handler = async (event: APIGatewayEvent, context: Context): Promise<APIGatewayProxyResult> => {
  console.log(`Event: ${JSON.stringify(event, null, 2)}`);
  console.log(`Context: ${JSON.stringify(context, null, 2)}`);

  let activeSpan: Span = trace.getActiveSpan()
  let activeSpanCtx = activeSpan.spanContext()
  console.log(activeSpan)
  console.log(activeSpanCtx)
  
  const response = {
      statusCode: 200,
      body: JSON.stringify({
        'msg':'success'
      }),
    };

  let myname = 'Hari';
  const apitest = await makeAPIcall( myname )
  
  return response;

};

async function invoke(
  requestBody: any,
  endpoint: string,
): Promise<any> {
  let response;
  try {
    response = await axios.post(endpoint, requestBody, {
      headers: {
        "Content-Type": "application/json"
      },
    });
  } catch (e) {
    console.log("Exception occured while invoking the api " + e);
    throw e;
  }
  return response;
}


// Must export via handler, required due to otel manipulating lambda entry point
//  https://github.com/open-telemetry/opentelemetry-js/issues/1946
module.exports = { handler }
