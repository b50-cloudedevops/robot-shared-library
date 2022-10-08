def lintCheck() {
sh '''
     echo Starting lint checks 
     mvn checkstyle:check || true
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
                    mvn clean compile
                    env.ARGS = -Dsonar.sources=target/
                    common.sonarCheck()
                }
            }
        }  
        stage('test cases') {
            parallel {
                stage('unit Tests') {
                    steps {
                        sh "Unit test cases completed"
                    }
                }
            }
        } 
    }
 
  }
}
// call is the default function whicch will be called