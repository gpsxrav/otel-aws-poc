import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { HelloWorld } from './hello-world';
import { updateName } from './update-name';

export class ServerlessCdkOtelStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new HelloWorld(this, 'hello-world');
    new updateName(this, 'update-name')

  }
}
