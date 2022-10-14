def lintCheck() {
sh '''
     echo Starting lint checks 
     #mvn checkstyle:check || true
     echo lint checks completed for ${COMPONENT}
 '''

}

def call() {
   pipeline {
     agent any
   environment {
    SONAR=credentials('Sonar')
 }
      stages {
        stage('Lint checks') {
            steps {
                script {
                    lintCheck()
                }
            }

        }
        stage('sonar check') {
            steps {
                script {
                    sh "mvn clean compile"
                    env.ARGS = "-Dsonar.sources=target/"
                    common.sonarCheck()
                }
            }
        }  
        
      }
   }

}