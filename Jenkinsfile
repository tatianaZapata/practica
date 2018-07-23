pipeline {
	agent {
		label 'Slave_Induccion'
	
	}
	
	options {
		buildDiscarder(logRotator(numToKeepStr: '3'))
		disableConcurrentBuilds()
	
	}
	
	tools {
		jdk 'JDK8_Centos'
		gradle 'Gradle4.5_Centos'
	
	}
	
	stages {
		stage('Checkout Code') {
			steps {
				echo "------------>Checkout<------------"
				checkout([$class: 'GitSCM', branches: [[name: '*/desarrollo']],
				doGenerateSubmoduleConfigurations: false, extensions: [], gitTool:
				'Git_Centos', submoduleCfg: [], userRemoteConfigs: [[credentialsId:
				'GitHub_tatianaZapata', url:
				'https://github.com/tatianaZapata/practica']]])
			}
		
		}
		
		stage('Compile') {
			steps{
				echo "------------>COMPILE<------------"
				sh 'gradle --b ./build.gradle compileJava'
			}
		}
		
		stage('Unit Tests') {
			steps {
				echo "------------>Unit Tests<------------"
				junit 'jacoco/test-results/*.xml cleanTest test'
			}
		
		}
		
		stage('Integration Tests') {
			steps {
				echo "INTEGRATION TESTS"
			
			}
		}
		
		stage('Static Code Analysis') {
			steps {
				echo "STATIC CODE ANALYSIS"
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner',type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties -Dsonar.organization=tatianaZapata-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=3ad73a9e242f288887a5"
				}
			}
		}
		
		stage('Build') {
			steps {
				echo "------------>Build<------------"
				sh 'gradle --b ./build.gradle build'

			}
		}
	}
	
	post {
		always {
			echo "This will always run"
		
		}
		
		success {
			echo 'This will run only if successful'
			junit 'build/test-results/*.xml'
		}
		
		failure {
			echo 'This will run only if failed'
			mail (to: 'tatiana.zapata@ceiba.com.co', subject:"Failed Pipeline:${currentBuild.fullDisplayName}", body: "Something is wrong with ${env.BUILD_URL}")

		}
		
		unstable {
			echo "run unstable"
		
		}
		
		changed {
			echo "Pipeline state has changed"
		
		}
	}
}