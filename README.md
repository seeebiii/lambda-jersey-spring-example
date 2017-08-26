# lambda-jersey-spring-example

This project shows hot to use [Jersey](https://jersey.github.io/) and [Spring](https://spring.io/) together in
[AWS Lambda](https://aws.amazon.com/lambda/) using [aws-serverless-java-container](https://github.com/awslabs/aws-serverless-java-container).


## Quick Start

```
    $ git clone git@github.com:seeebiii/lambda-jersey-spring-example.git
    $ cd /path/to/repo
    $ ./deploy.sh <BUCKET_NAME> [STACK_NAME]
```

#### Options
- **BUCKET_NAME:** a S3 bucket name to upload the JAR file to, e.g. `[your-name]-lambdas`
- **STACK_NAME:** a name which identifies the stack you are creating with the CloudFormation template. Default: *lambda-jersey-spring-example*

Then just access the resources as printed in your output:

```
    Test resource one at: https://xxx.execute-api.us-east-1.amazonaws.com/Prod/one
    Test resource two at: https://xxx.execute-api.us-east-1.amazonaws.com/Prod/two
```


## Description
Take a look at [JerseySpringHandler](src/main/java/de/sebastianhesse/aws/examples/JerseySpringHandler.java) which contains the following important code:
```java
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
```


## License

MIT License

Copyright (c) 2017 [Sebastian Hesse](https://www.sebastianhesse.de)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.