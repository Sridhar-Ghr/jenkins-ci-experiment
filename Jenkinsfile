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
            def branchName = env.BRANCH_NAME ?: env.GIT_BRANCH

            if (!branchName || branchName == 'HEAD') {
                def ref = bat(script: 'git symbolic-ref --short HEAD || git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                branchName = ref
            }

            echo "Resolved branch name: ${branchName}"

            // Strip 'origin/' if present
            branchName = branchName.replaceFirst(/^origin\//, '')

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