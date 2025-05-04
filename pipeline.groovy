pipeline {
    agent any 
    stages {
        stage ("Code-Pull") {
            steps {
                git branch: 'dev', url: 'https://github.com/pratik-85/project-backend.git'
            }
        }
        stage ("Code-Build") {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ("Deploy-K8S") {
            sh '''
            docker build . -t pratiik/project-backend-img:latest
            docker push pratiik/project-backend-img:latest
            docker rmi pratiik/project-backend-img:latest
            kubectl apply -f ./deploy/
            '''
        }
    }
}
