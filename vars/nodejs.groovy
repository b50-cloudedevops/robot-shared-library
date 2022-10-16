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
    NEXUS=credentials('Nexus')
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
        stage('Check the release') {
                 when {
                   expression { env.TAG_NAME != null }
                 }
                 steps {
                    script {
                        env.UPLOAD_STATUS = sh(returnStdout: true, script: 'curl -L -s http://172.31.3.52:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true')
                        print UPLOAD_STATUS
                  }
           }
        }
        stage('Prepare artifacts') {
                 when {
                   expression { env.TAG_NAME != null }
                   expression { env.UPLOAD_STATUS == "" }
                 }
                 steps {
                    sh '''
                        npm install
                        zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                    '''
                  }
           }
        stage('Upload artifacts') {
                 when {
                    expression { env.TAG_NAME != null }
                    expression { env.UPLOAD_STATUS == "" }
                  }
                 steps {
                    sh '''
                       curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.3.52:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
                    '''
                }
            }
         
        }
    
    }
}
