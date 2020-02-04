import groovy.json.*
node{
    def flowdsl = '''\
		project "Default",{
		  pipeline "Jenkins-triggered",{
			formalParameter "InputParam"
			stage "Stage 1",{
			  task "command",{
				actualParameter = [
				  'commandToRun': 'echo Triggered from Jenkins, $[InputParam]',
				]
				taskType = 'COMMAND'
			  }
			}
		  }
		}
    '''.stripIndent()
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