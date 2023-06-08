import { Construct } from 'constructs';
import { NodejsFunction } from 'aws-cdk-lib/aws-lambda-nodejs';
import { LambdaRestApi } from 'aws-cdk-lib/aws-apigateway';
import * as lambda from 'aws-cdk-lib/aws-lambda'
import { Architecture, Runtime } from 'aws-cdk-lib/aws-lambda';
import { AttributeType, BillingMode, Table } from 'aws-cdk-lib/aws-dynamodb';
import { Duration } from 'aws-cdk-lib';

export class HelloWorld extends Construct {
  constructor(scope: Construct, id: string) {
    super(scope, id);

    const table = new Table(scope, 'example-table', {
      billingMode: BillingMode.PAY_PER_REQUEST,
      partitionKey: {
        name: 'id',
        type: AttributeType.STRING
      }
    })

    const helloFunction = new NodejsFunction(this, 'function', {
      runtime: Runtime.NODEJS_16_X,
      architecture: Architecture.ARM_64,
      memorySize: 10240,
      timeout: Duration.seconds(20),
      environment: {
        // Required for layer
        AWS_LAMBDA_EXEC_WRAPPER: '/opt/otel-handler',
        OPENTELEMETRY_COLLECTOR_CONFIG_FILE: '/var/task/collector.yaml',

        // Required to for HNY
        OTEL_PROPAGATORS: 'tracecontext',
        OTEL_SERVICE_NAME: 'sre-frontend',
        OTEL_TRACES_SAMPLER: 'always_on',

        // Standard environment variable
        DDB_TABLE_NAME: table.tableName
      },
      layers: [
        // From https://github.com/aws-observability/aws-otel-lambda
        lambda.LayerVersion.fromLayerVersionArn(
          this,
          'otel-layer',
          'arn:aws:lambda:us-west-2:901920570463:layer:aws-otel-nodejs-arm64-ver-1-12-0:1'
        ),
      ],
      // Ignores AWS Lambda services' OTEL traces
      tracing: lambda.Tracing.PASS_THROUGH,
      bundling: {
        keepNames: true,
        nodeModules: [
          // For Otel's auto-instrumentation to work the package must be in node modules
          // Packages that autoinstrumentation will work on https://www.npmjs.com/package/@opentelemetry/auto-instrumentations-node
          '@aws-sdk/client-dynamodb',
          '@aws-sdk/client-eventbridge',
          '@aws-sdk/client-route-53',
          '@aws-sdk/client-s3',
          '@aws-sdk/client-sns',
          '@aws-sdk/client-sqs',
          '@aws-sdk/client-appconfig',
          '@aws-sdk/client-appconfigdata',
        ],
        externalModules: [
          // Do not deploy, runtime function will use these values from the layer
          //  we have these deps in our package.json so that we can add
          //  OTel types to code + use honeycomb for local invokes of the lambda function
          '@opentelemetry/api',
          '@opentelemetry/sdk-node',
          '@opentelemetry/auto-instrumentations-node',
        ],
        commandHooks: {
          // AWS Otel lambda, this for otel configuration
          beforeBundling(inputDir: string, outputDir: string): string[] {
            return [`cp ${inputDir}/collector.yaml ${outputDir}`]
          },
          afterBundling(): string[] {
            return []
          },
          beforeInstall() {
            return []
          },
        },
      }
    });

    table.grantReadWriteData(helloFunction)

    new LambdaRestApi(this, 'apigw', {
      handler: helloFunction,
    });

  }
}
