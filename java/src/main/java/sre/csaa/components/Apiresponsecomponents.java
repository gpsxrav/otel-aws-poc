package sre.csaa.components;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Apiresponsecomponents {
    protected APIGatewayProxyResponseEvent ok(JSONObject response) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(response.toString())
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent okCorsEnabled(JSONObject response) {
        HashMap headers = new LinkedHashMap();
        headers.put("Access-Control-Allow-Origin", "*");
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(headers)
                .withBody(response.toString())
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent okCorsEnabledMethod(JSONObject response) {
        HashMap headers = new LinkedHashMap();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods","GET,POST,OPTIONS");
        headers.put("Access-Control-Allow-Headers","Content-Type,Authorization,X-Api-Key");
        headers.put("Access-Control-Allow-Credentials","true");
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(headers)
                .withBody(response.toString())
                .withIsBase64Encoded(false);
    }


    protected APIGatewayProxyResponseEvent notfound(JSONObject response) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(404)
                .withBody(response.toString())
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent error(JSONObject response, Exception exc) {
        String exceptionString = String.format("error: %s: %s", exc.getMessage(), Arrays.toString(exc.getStackTrace()));
        response.put("Exception", exceptionString);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(500)
                .withBody(response.toString())
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent errorFormatted(String htmlContent, HashMap headers) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(500)
                .withBody(htmlContent)
                .withHeaders(headers)
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent okFormatted(String htmlContent, HashMap headers) {
        headers.put("Access-Control-Allow-Origin", "*");
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(htmlContent)
                .withHeaders(headers)
                .withIsBase64Encoded(false);
    }

    protected APIGatewayProxyResponseEvent okJSONArray(JSONArray jArray) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(jArray.toString())
                .withIsBase64Encoded(false);
    }
}
