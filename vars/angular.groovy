def lintCheck() {
sh '''
     echo Starting lint checks for ${COMPONENT}
     echo lint checks completed for ${COMPONENT}
 '''

}

def call() {
   pipeline {
     agent any
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
                    env.ARGS = "-Dsonar.sources=target/"
                    common.sonarCheck()
                }
            }
        } 
        stage('test cases') {
            parallel {
                stage('unit Tests') {
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
// call is the default function whicch will be called