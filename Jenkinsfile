pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '23mis0160/payment-service'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        // Path to kubeconfig inside workspace (used by withKubeConfig)
        KUBECONFIG = credentials('kubeconfig')
    }

    tools {
        maven 'Maven-3.8'
        jdk 'JDK11'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            when {
                branch 'main'
            }
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-credentials') {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Deploy to Minikube (Staging)') {
            when {
                branch 'main'
            }
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh """
                        kubectl apply -f kubernetes/deployment.yaml -n staging
                        kubectl set image deployment/payment-service payment-service=${DOCKER_IMAGE}:${DOCKER_TAG} -n staging
                        kubectl apply -f kubernetes/service.yaml -n staging
                        kubectl rollout status deployment/payment-service -n staging --timeout=120s
                    """
                }
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful! Check service with: minikube service payment-service -n staging'
        }
        failure {
            echo '❌ Pipeline failed. Check logs.'
        }
    }
}