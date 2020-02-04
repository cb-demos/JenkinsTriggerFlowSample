import groovy.json.*

node{
	step(checkout([$class: 'GitSCM', branches: [[name: '*/master']],userRemoteConfigs: [[url: 'https://github.com/cb-demos/JenkinsTriggerFlowSample.git']]]))
	/*
	def flowdsl = new File("FlowPipeline.groovy").text
	def body = new JsonBuilder( [overwrite: true, dsl: flowdsl] ).toString()	
	step([$class: 'ElectricFlowGenericRestApi', 
			configuration: 'Colocated Flow',
			urlPath : '/server/dsl',
			httpMethod : 'POST',
			body : body,
			envVarNameForResult:'FLOW_REST_RESPONSE'
		])
	*/	
	step([$class: 'ElectricFlowPipelinePublisher', 
		configuration: 'Colocated Flow',
		projectName: 'Default',
		pipelineName: 'Jenkins-triggered',
		addParam: '{"pipeline":{"pipelineName":"Jenkins-triggered","parameters":"[{\\\"parameterName\\\": \\\"InputParam\\\", \\\"parameterValue\\\": \\\"xyz\\\"}]"}}'
	])
}
