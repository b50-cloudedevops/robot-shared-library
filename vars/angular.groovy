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
    }
}
}
// call is the default function whicch will be called