**Set-up demo project**


1. Replace `xxx` in `collector.yaml` to your honeycomb keys - Pradeep - Done 
1. NOTE: Must use us-west-2 as this is where the lambda layer is  - Pradeep - Done 
1. `npm install` -- Installs dependencies
1. `npx cdk deploy` -- Deploys the stack 
1. Hit the URL that CDK outputs from the deployment. You should see a counter
1. Review honeycomb, your traces should be present


**How to use this demo**

This demo project is a reference for the simplest possible set up. Review the /lib directory for changes you will need to make to your application.

**Additional benefits to the project**

- Provides a TYPED decorator style function (spanify) to reduce nesting from `startActiveSpan`
    - Spanify provides a sane default exception handling
- Adds TS typing back to the `@opentelemetry/api` methods
