project 'Kubernetes Example',{
	pipeline "Jenkins-triggered",{
	formalParameter "InputParam"
	stage "Dev",{
	
		task "Approve",{
			taskType = 'MANUAL'
			approver = [
				'admin',
			]
		}		
	
		task 'Deploy App', {
			description = ''
			actionLabelText = null
			actualParameter = [
				'ec_enforceDependencies': '1',
				'ec_smartDeployOption': '1',
				'ec_stageArtifacts': '0',
				'nginx_version': '1.7.9',
			]
			environmentName = 'dev'
			environmentProjectName = projectName
			subapplication = 'Kubernetes App'
			subprocess = 'Deploy'
			subproject = projectName
			taskProcessType = 'APPLICATION'
			taskType = 'PROCESS'
		}
	}
	}
}