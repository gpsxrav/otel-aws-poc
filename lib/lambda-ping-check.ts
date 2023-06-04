import { Construct } from 'constructs';
import * as lambda from 'aws-cdk-lib/aws-lambda'
import { Duration } from 'aws-cdk-lib';
import { Pigeon } from 'cdk-pigeon';
import * as events from 'aws-cdk-lib/aws-events';
import * as path from 'path';

export class lambdapingcheck extends Construct {
  constructor(scope: Construct, id: string) {
    super(scope, id);
    const url = 'www.google.com';

    new Pigeon(this, 'pigeon-monitor', {
      schedule: events.Schedule.rate(Duration.minutes(5)),
      alertOnFailure: true,
      emailAddress: 'pradeep.ravichandran@csaa.com',
      lambdaFunctionProps: {
        //code: lambda.Code.fromAsset(path.join(__dirname, '/monitoring')),
        entry: (path.join(__dirname, '/monitoring/lambda-ping-check-function.ts')).toString,
        handler: 'handler.http',
        runtime: lambda.Runtime.NODEJS_14_X,
        // bundling: {
        //   keepNames: true,
        //   nodeModules: [
        //     'request']
        // },
        environment: {
          'URL': url,
        },
      },
    });



  }
}
