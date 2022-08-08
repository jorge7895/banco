pipeline {
    agent any
    tools {
    	maven "Maven inicial"
    	jdk 'OpenJDK 11 inicial'
    }
    stages {
    	stage('CompilaciÃ³n y ejecuciÃ³n de test') {
    		steps {
				sh 'mvn clean test'
    		}
    	}
    }
    
    post {
       always {
          junit(
        	allowEmptyResults: true,
        	testResults: 'target/surefire-reports/*.xml',
        	skipPublishingChecks: true
      	)
      }
   }     
}
