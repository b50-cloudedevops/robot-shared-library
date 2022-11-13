//def call() {
//   pipeline {
//    agent any
//      stages {
//      stage('Lint checks') {
//        steps {
//          script {
//            lintCheck()
//      }
//    }

//        }
//     stage('sonar check') {
//            steps {
//                script {
//                    env.ARGS = "-Dsonar.sources=."
//                    common.sonarCheck()
//                }
//            }
//        }   

//      stage('test cases') {
//            parallel {
//                stage('unit Tests') {
//                   steps {
//                        sh "echo Unit test cases completed"
//                   }
//                }
//                stage('Integration Tests') {
//                    steps {
//                        sh "echo Integration test cases completed"
//                    }
//                }
//                stage('Functional Tests') {
//                    steps {
//                        sh " echo Functional test cases completed"
//                    }
//                }
//            }
//        }        
//    }
//    }
//}
// call is the default function whicch will be called
def call() {
    node {
        git branch: 'main', url: "https://github.com/b50-cloudedevops/${COMPONENT}.git"
        env.APPTYPE="python"
        common.sonarCheck()
        common.lintCheck()
        env.ARGS="-Dsonar.sources=."
        common.testCases()
        if (env.TAG_NAME != null) {
            common.artifact()
        }
    }
}