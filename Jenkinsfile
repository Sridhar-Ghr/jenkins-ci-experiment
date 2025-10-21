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
        expression {
            return env.BRANCH_NAME == 'development'
        }
    }
    steps {
        script {
            def targetBranch = 'main'
            echo "Branch name: ${env.BRANCH_NAME}"
            bat """
                git config user.name "Sridhar-Ghr"
                git config user.email "sridhar.cisms@gmail.com"
                git fetch origin
                git checkout ${targetBranch}
                git merge origin/${env.BRANCH_NAME}
                git push origin ${targetBranch}
            """
        }
    }
}
    }
}