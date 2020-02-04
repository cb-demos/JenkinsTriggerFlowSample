import groovy.json.*

node{
    git url: 'https://github.com/cb-demos/JenkinsTriggerFlowSample.git'
	env.WORKSPACE = pwd()
	
	["FlowPipeline","KubernetesApplication"].each { file ->
		def flowdsl = readFile "${env.WORKSPACE}/${file}.groovy"
		def RestBody = new JsonBuilder( [overwrite: true, dsl: flowdsl] ).toString()	
		step([$class: 'ElectricFlowGenericRestApi', 
				configuration: 'Colocated Flow',
				urlPath : '/server/dsl',
				httpMethod : 'POST',
				body : RestBody,
				envVarNameForResult:"${file}_RESPONSE"
			])
	}
	
	step([$class: 'ElectricFlowPipelinePublisher', 
		configuration: 'Colocated Flow',
		projectName: 'Default',
		pipelineName: 'Jenkins-triggered',
		addParam: '{"pipeline":{"pipelineName":"Jenkins-triggered","parameters":"[{\\\"parameterName\\\": \\\"nginx_version\\\", \\\"parameterValue\\\": \\\"1.7.9\\\"}]"}}'
	])
}
