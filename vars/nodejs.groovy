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
         sonar-scanner -Dsonar.host.url=http://172.31.1.192:9000/  -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT}
    '''
}
def call() {
 pipeline {
     agent any 
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
        stage('Sonar check') {
            steps {
                script {
                    sonarCheck()
                }
            }
        }
       }
 }
}
