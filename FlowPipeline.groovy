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