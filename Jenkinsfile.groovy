/*

	Jenkinsfile DSL - Creates Jenkins Pipeline that build Flow Application and Pipeline models then trigger the Flow Pipeline

*/

/* 
	Flow DSL overwrite should be set to true to make sure that models are exactly implemented according to the specified DSL (not incremental). However, due to an issue with this feature (https://cloudbees.atlassian.net/browse/CEV-24352), we will
	use false.
*/
def DslOverwrite = false

import groovy.json.*

node{
    git url: 'https://github.com/cb-demos/JenkinsTriggerFlowSample.git'
	env.WORKSPACE = pwd()
	
	["KubernetesApplication","FlowPipeline"].each { file ->
		def flowdsl = readFile "${env.WORKSPACE}/${file}.groovy"
		def RestBody = new JsonBuilder( [overwrite: DslOverwrite, dsl: flowdsl] ).toString()	
		step([$class: 'ElectricFlowGenericRestApi', 
				configuration: 'Colocated Flow',
				urlPath : '/server/dsl',
				httpMethod : 'POST',
				body : RestBody,
				envVarNameForResult:"${file}_RESPONSE"
			])
	}
	
	def Params = new JsonBuilder(
		[ pipeline: [
			pipelineName: "Jenkins-triggered",
			parameters: [
				parameterName: "nginx_version",
				parameterValue: "1.7.9"
				]
			]
		]
	).toString()
	step([$class: 'ElectricFlowPipelinePublisher', 
		configuration: 'Colocated Flow',
		projectName: 'Kubernetes Example',
		pipelineName: 'Jenkins-triggered',
		//addParam: '{"pipeline":{"pipelineName":"Jenkins-triggered","parameters":"[{\\\"parameterName\\\": \\\"nginx_version\\\", \\\"parameterValue\\\": \\\"1.7.9\\\"}]"}}'
	      	addParam: Params
	])
}
