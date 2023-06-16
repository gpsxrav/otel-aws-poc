package sre.csaa.otel;

import java.util.Objects;

import org.crac.Core;
import org.crac.Resource;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sre.csaa.components.Apiresponsecomponents;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

public class SampleLambda extends Apiresponsecomponents
        implements RequestHandler<Object, APIGatewayProxyResponseEvent> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JSONObject response;
    JSONObject appConfig;
    Tracer tracer = GlobalOpenTelemetry.getTracer("my-service");

    @Override
    public APIGatewayProxyResponseEvent handleRequest(Object event,
            com.amazonaws.services.lambda.runtime.Context context) {
        JSONObject response = new JSONObject();
        String ss = gson.toJson(event);
        JSONObject obj = new JSONObject(ss);
        JSONObject queryStringParametersObject = obj.getJSONObject("queryStringParameters");

        response = new JSONObject();
        try {
            System.out.println(queryStringParametersObject);
            String name = queryStringParametersObject.getString("name");
            String traceparenObject = queryStringParametersObject.getString("traceparent");
            // Span span = Span.current();
            // span.setAttribute("name", "java-lambda");
            // System.out.println(io.opentelemetry.context.Context.current());
            // span.end();
            System.out.println(traceparenObject);
            // Span parentSpan = Span.wrap(SpanContext.createFromRemoteParent(
            // traceparenObject.split("-")[1],
            // traceparenObject.split("-")[2],
            // TraceFlags.getDefault(),
            // TraceState.getDefault()));
            // parentSpan.setAttribute("nameToUpdate", name);
            // System.out.println(parentSpan);
            // parentSpan.end();

            response.put("msg", "otel test");
        } catch (Exception e) {
            response.put("info", "error");
            e.printStackTrace();
            return error(response, e);
        }
        return okCorsEnabledMethod(response);
    }

}
