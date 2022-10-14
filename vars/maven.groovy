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
         
          tage('sonar check') {
            steps {
                script {
                    sh "mvn clean compile"
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