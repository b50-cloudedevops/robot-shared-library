def call() {
pipeline {
        agent any 
   parameters {
        string(name: 'VERSION', defaultValue: '', description: 'Chose the version to deploy')
        string(name: 'ENV', defaultValue: 'dev', description: 'Chose the ENV to deploy')
    }
        stages {
            stage('Deploying Component') {
                steps {
                    sh "echo Authenticating to $ENV EKS"
                    sh "aws eks update-kubeconfig --name ${ENV}-eks-cluster"
                    sh "kubectl get nodes"
                    sh "sed -e 's/VERSION/${VERSION}/' k8-deploy.yml > deploy.yml"
                    sh "kubectl apply -f deploy.yml"
                }
            }

        }    // end of statges 
    }
}