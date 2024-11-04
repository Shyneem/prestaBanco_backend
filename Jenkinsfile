pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages{
        stage('Build maven'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Shyneem/prestaBanco_backend.git']])
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test -Dspring.profiles.active=test -e -X'
            }
        }

        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t shyneem/prestabanco_backend:latest .'
                }
            }
        }
        stage('Push image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                    }
                    bat 'docker push shyneem/prestabanco_backend:latest'
                }
            }
        }
    }
}