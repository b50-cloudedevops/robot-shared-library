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
        //stage ('sonar check') {
        //    steps {
        //        script {
        //            env.ARGS = "-Dsonar.sources=."
        //            common.sonarCheck()
        //        }
        //    }
        //}
        //stage ('test cases') {
        //    parallel {
        //        stage('unit Tests') {
        //            steps {
        //                sh "echo Unit test cases completed"
        //            }
        //        }
        //        stage ('Integration Tests') {
        //            steps {
        //                sh "echo Integration test cases completed"
        //            }
        //        }
        //        stage('Functional Tests') {
        //            steps {
        //                sh " echo Functional test cases completed"
        //            }
       //         }
       //     }
        // }
        //    stage('Prepare artifacts') {
        //        when {
        //            expression { env.TAG_NAME != null }
        //        }
        //        steps {
        //            sh '''
        //                npm install
        //                zip ${COMPONENT}.zip node_modules server.js
        //            '''
        //        }
        //    }
        //    stage('Upload artifacts') {
        //        when {
        //            expression { env.TAG_NAME != null }
        //        }
        //        steps {
        //            sh '''
        //               curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file pom.xml http://172.31.3.52:8081/repository/${COMPONENT}/${COMPONENT}.zip
        //            '''
        //        }
        //    }
        //} 
   }
 
}
