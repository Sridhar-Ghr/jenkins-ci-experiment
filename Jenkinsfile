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
            return script {
                // Try Jenkins-provided env vars
                def branchName = env.BRANCH_NAME ?: env.GIT_BRANCH

                // If still null or 'HEAD', parse from git manually
                if (!branchName || branchName == 'HEAD') {
                    def ref = bat(script: 'git symbolic-ref --short HEAD || git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    branchName = ref
                }

                echo "Resolved branch name: ${branchName}"
                return branchName == 'development'
            }
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