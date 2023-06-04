import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { HelloWorld } from './hello-world-two';
import { updateName } from './update-name';
import { hifn } from './hi';

export class ServerlessCdkOtelStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new HelloWorld(this, 'hello-world-two');
    // new updateName(this, 'update-name-two')
    // new hifn(this, 'hi-two')

  }
}
