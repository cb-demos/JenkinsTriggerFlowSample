project 'Kubernetes Example',{
	pipeline "Jenkins-triggered",{
		formalParameter "nginx_version"
		formalParameter "build_id"
		stage "Dev",{
		
			task "Approve",{
				taskType = 'MANUAL'
				approver = [
					'admin',
				]
			}		
		
			task 'Deploy App', {
				actualParameter = [
					'ec_enforceDependencies': '1',
					'ec_smartDeployOption': '1',
					'ec_stageArtifacts': '0',
					'nginx_version': '$[nginx_version]',
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
