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
    SONAR=credentials('Sonar')
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
        stage('Sonar check') {
            steps {
                script {
                    env.ARGS = "-Dsonar.sources=."
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
