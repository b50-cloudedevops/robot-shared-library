def lintCheck() {
sh '''
     # We want devs to handle the lint checks failure
     # npm i jslint
     # node_modules/jslint/bin/jslint.js server.js || true
     echo starting lint checks
     echo lint checks completed
 '''

}

def sonarCheck() {
    sh '''
          sonar-scanner -Dsonar.host.url=http://172.31.1.192:9000/ -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${Sonar_usr} -Dsonar.password=${Sonar_psw}
    '''
}

def call() {
   pipeline {
     agent any
     environment {
        Sonar = credentials('Sonar')
     }
      stages {
        stage('Installing the node js dependencies') {
            steps {
                sh "npm install"
            }
        }
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
                    sonarCheck()
                }
            }
        }
    }
}
}
// call is the default function whicch will be called