pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Merge if Successful') {
            when {
                branch 'Development'
            }
            steps {
                script {
                    def targetBranch = 'main'
                    sh """
                        git config user.name "jenkins-bot"
                        git config user.email "jenkins@example.com"
                        git checkout ${targetBranch}
                        git merge ${env.GIT_BRANCH}
                        git push origin ${targetBranch}
                    """
                }
            }
        }
    }
}