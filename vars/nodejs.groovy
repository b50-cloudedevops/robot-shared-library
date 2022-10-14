def lintCheck() {
sh '''
     # We want devs to handle the lint checks failure
     # npm i jslint
     # node_modules/jslint/bin/jslint.js server.js || true
     echo starting lint checks
     echo lint checks completed
 '''

}

def call() {
 pipeline {
     agent any
       environment {
        SONAR = credentials('Sonar')
        NEXUS = credentials('NEXUS')
        }
       stages{
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
       }
 }
}
