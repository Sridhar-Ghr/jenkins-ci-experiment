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
                bat 'mvn clean install'
            }
        }

        stage('Merge if Successful') {
            when {
                branch 'origin/Development'
            }
            steps {
                script {
                    def targetBranch = 'main'
                    bat """
                        git config user.name "Sridhar-Ghr"
                        git config user.email "sridhar.cisms@gmail.com"
                        git checkout ${targetBranch}
                        git merge ${env.GIT_BRANCH}
                        git push origin ${targetBranch}
                    """
                        }
                  }    
            steps{
                echo "Branch name: ${env.BRANCH_NAME}"
            }
        }
    }
}