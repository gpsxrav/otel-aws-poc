package sre.csaa.otel;

import java.util.Objects;

import org.crac.Core;
import org.crac.Resource;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sre.csaa.components.Apiresponsecomponents;

public class SampleLambda extends Apiresponsecomponents implements RequestHandler<Object, APIGatewayProxyResponseEvent>, Resource
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JSONObject response;
    JSONObject appConfig;
    
    public SampleLambda() {
        Core.getGlobalContext().register(this);
      }

    @Override
    public void beforeCheckpoint(org.crac.Context<? extends Resource> arg0) throws Exception {
        System.out.println("Before checkpoint");    
    }

    @Override
    public void afterRestore(org.crac.Context<? extends Resource> arg0) throws Exception {
        System.out.println("After restore");
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(Object event, Context context) {
        response = new JSONObject();
        
        try {   
            response.put("message", "Otel test");
        }catch(Exception e){
            response.put("info", "");
            e.printStackTrace();
            return error(response, e);
        }
        return ok(response);
    }

}



