import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { lambdapingcheck } from './lambda-ping-check';

export class Pingtest extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new lambdapingcheck(this, 'lambda-ping-check');
  

  }
}
