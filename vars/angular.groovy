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
                    env.ARGS = -Dsonar.sources=target/
                    common.sonarCheck()
                }
            }
        }   
    }
}
}
// call is the default function whicch will be called