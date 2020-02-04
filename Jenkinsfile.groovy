import groovy.json.*
node{
    def flowdsl = new File("FlowPipeline.groovy")
	def body = new JsonBuilder( [overwrite: true, dsl: flowdsl] ).toString()
	step([$class: 'ElectricFlowGenericRestApi', 
            configuration: 'Colocated Flow',
            urlPath : '/server/dsl',
            httpMethod : 'POST',
            body : body,
            envVarNameForResult:'FLOW_REST_RESPONSE'
        ])
	step([$class: 'ElectricFlowPipelinePublisher', 
		configuration: 'Colocated Flow',
		projectName: 'Default',
		pipelineName: 'Jenkins-triggered',
		addParam: '{"pipeline":{"pipelineName":"Jenkins-triggered","parameters":"[{\\\"parameterName\\\": \\\"InputParam\\\", \\\"parameterValue\\\": \\\"xyz\\\"}]"}}'
	])
}