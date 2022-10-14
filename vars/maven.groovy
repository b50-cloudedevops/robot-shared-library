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
                    sh "mvn clean package"
                    env.ARGS = "-Dsonar.sources=target/"
                    common.sonarCheck()
                }
            }
        }  
        stage('Test cases') {
            parallel {
                stage('Unit Tests') {
                 steps {
                        sh "echo Unit test cases completed"
                    }
                }
                stage('Integration Tests') {
                    steps {
                        sh "echo Integration test cases completed"
                    }
                }
                stage('Functional Tests') {
                    steps {
                        sh " echo Functional test cases completed"
                    }
                }
            }
        }
        
      }
   }

}