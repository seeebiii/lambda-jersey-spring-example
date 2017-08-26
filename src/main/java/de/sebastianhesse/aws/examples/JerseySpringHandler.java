package de.sebastianhesse.aws.examples;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


/**
 * A request handler loading a Spring context and using spring supported Jersey resources.
 */
public class JerseySpringHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;


    public JerseySpringHandler() {
        // create Spring context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();

        // use Spring bean of JerseyResourceConfig to have spring supported resources
        JerseyResourceConfig resourceConfig = context.getBean(JerseyResourceConfig.class);
        handler = JerseyLambdaContainerHandler.getAwsProxyHandler(resourceConfig);
    }


    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        return handler.proxy(awsProxyRequest, context);
    }

}
