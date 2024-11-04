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
        stage('Push image to Docker Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'docker-credentials', variable: 'docker-credentials|')]) {
                        bat 'docker login -u shyneem -p %docker-credentials%'
                   }
                   bat 'docker push shyneem/prestabanco_backend:latest'
                }
            }
        }
    }
}