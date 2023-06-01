**Set-up demo project**

1. Copy + Paste `collector-template.yaml` to `collector.yaml` - Pradeep - Done 
1. Replace `xxx` in `collector.yaml` to your honeycomb keys - Pradeep - Done 
1. NOTE: Must use us-west-2 as this is where the lambda layer is  - Pradeep - Done 
1. `npm install` -- Installs dependencies
1. `npx cdk deploy` -- Deploys the stack 
1. Hit the URL that CDK outputs from the deployment. You should see a counter
1. Review honeycomb, your traces should be present

In the sandbox, you can see the https://ml2xigrkr9.execute-api.us-west-2.amazonaws.com/prod/

This uses the API spun from other stack
SRE-AutomationCDK-TS-OTEL4.urlsreloginvocationpy = https://raum26pihb.execute-api.us-west-2.amazonaws.com/otel4/logInvocationPyApiotel4
SRE-AutomationCDK-TS-OTEL4.urlsreupdatenamejava = https://raum26pihb.execute-api.us-west-2.amazonaws.com/otel4/updateNameJavaApiotel4
SRE-AutomationCDK-TS-OTEL4.urlsreupdatenameotel4 = https://raum26pihb.execute-api.us-west-2.amazonaws.com/otel4/updateNameTSApi
SRE-AutomationCDK-TS-OTEL4.urlsreupdatenamewrapperotel4 = https://raum26pihb.execute-api.us-west-2.amazonaws.com/otel4/updateNameWrapperTSApi

**How to use this demo**

This demo project is a reference for the simplest possible set up. Review the /lib directory for changes you will need to make to your application.

**Additional benefits to the project**

- Provides a TYPED decorator style function (spanify) to reduce nesting from `startActiveSpan`
    - Spanify provides a sane default exception handling
- Adds TS typing back to the `@opentelemetry/api` methods
