import { Context, APIGatewayProxyResult, APIGatewayEvent } from 'aws-lambda';
import { DynamoDBDocumentClient, GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import axios from 'axios';
import {W3CTraceContextPropagator} from "@opentelemetry/core";
import {
    defaultTextMapGetter,
    defaultTextMapSetter,
    ROOT_CONTEXT,
    SpanContext,
} from "@opentelemetry/api";

const { trace, SpanStatusCode, propagation, context } = require('@opentelemetry/api')
import type { Span, SpanOptions } from '@opentelemetry/api'

const tracer = trace.getTracer('my-service-tracer')

const makeAPIcall = async function makeAPIcall(myname: string): Promise<string> {

  let endpoint = "https://tow8go7doh.execute-api.us-west-2.amazonaws.com/prod/";
  let request = {
    name: myname
  }

  let response = await invoke(request, endpoint);
  console.log(response);
  return myname
}


const handler = async (event: APIGatewayEvent, rcontext: Context): Promise<APIGatewayProxyResult> => {
  console.log(`Event: ${JSON.stringify(event, null, 2)}`);
  console.log(`Context: ${JSON.stringify(rcontext, null, 2)}`);
  let corsHeaders = {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET,POST,OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type,Authorization,X-Api-Key,traceparent",
    "Access-Control-Allow-Credentials": "true"
}

  if (!event.queryStringParameters?.name) {
    return {
      statusCode: 400,
      body: JSON.stringify({
        'msg':'bad request'
      }),
      headers: corsHeaders
    };;
  }

  // Print active span and its context to console for debugging

  // let activeSpan: Span = trace.getActiveSpan()
  // let activeSpanCtx = activeSpan.spanContext()
  // console.log(activeSpan)
  // console.log(activeSpanCtx)
 
  // get span from active context
  let span: Span = trace.getSpan(context.active())

  // update the default span name with custom name
  span.setAttribute("name", "api-1")

  let myname = event.queryStringParameters?.name;

  // Add custom attributes to the span. 
  // Note: value can be seen in traces with attribute key same as the varibale name. 
  // in below case we can find the attribute named "myname" included under the span
  span.setAttributes({myname})
  const response = {
      statusCode: 200,
      body: JSON.stringify({
        'msg':'success'
      }),
      headers: corsHeaders
    };

  
  const apitest = await makeAPIcall( myname )

  // Span end is not mandatory unless we wish to add more child spans under this active span
  //span.end()
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
