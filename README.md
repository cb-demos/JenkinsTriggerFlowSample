# JenkinsTriggerFlowSample

This repository illustrates how to both create and invoke Flow Pipelines from a Jenkins pipeline.

## Functionality
The file Jenkinsfile.groovy implements a Jenkins Pipeline that creates a Flow Application and Environment models from the file KubernetesApplication.groovy, creates a Flow Pipeline from the file FlowPipeline.groovy, then invokes the Flow pipeline.The Flow pipeline invocation includes a parameter which is also passed to the Flow Application model. In a complete CI/CD implmentation, this Jenkins pipeline would include a build and the build version would be passed to the Flow pipeline.

## Set up
1. Clone this repository
1. Create a Jenkins Pipeline Job that points to your repository as the "Pipeline script from SCM" as the Definition and uses Jenkinsfile.groovy as the "Script Path"
1. Update FlowPipeline.groovy and KubernetesApplication.groovy to implement your particular CloudBees Flow deployment and pipeline models
1. Run the Jenkins Pipeline
