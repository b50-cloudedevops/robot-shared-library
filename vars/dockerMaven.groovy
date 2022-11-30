def call() {
    node {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/b50-clouddevops/${COMPONENT}.git"
        
        stage('Preparing Binary') {
            sh "mvn clean package"
            sh "mv target/shipping-1.0.jar shipping.jar"
        }
        stage('Docker Build') {
            sh "docker build -t 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
            sh "docker images"
        }
        if (env.TAG_NAME != null) {
            stage('Docker Push') {
                sh "docker tag 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest  355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 355449129696.dkr.ecr.us-east-1.amazonaws.com"
                sh "docker push 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
            }
        }
    }
}