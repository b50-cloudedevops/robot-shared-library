# robot-shared-library
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