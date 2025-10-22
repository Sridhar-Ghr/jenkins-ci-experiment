pipeline {
    agent any

    stages {
            stage('Setup') {
            steps {
                bat 'git config --global --add safe.directory "C:/ProgramData/Jenkins/.jenkins/workspace/"'
            }
        }
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

                        branchName = branchName.replaceFirst(/^origin\//, '')
                        echo "Resolved branch name for 'when' check: ${branchName}"

                        return branchName == 'development'
                    }
                }
            }

            steps {
                script {
                    // Re-resolve the branch name for use in this block
                    def sourceBranch = env.BRANCH_NAME ?: env.GIT_BRANCH

                    if (!sourceBranch || sourceBranch == 'HEAD') {
                        def ref = bat(script: 'git symbolic-ref --short HEAD || git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                        sourceBranch = ref
                    }

                    sourceBranch = sourceBranch.replaceFirst(/^origin\//, '')
                    def targetBranch = 'main'

                    echo "Merging ${sourceBranch} into ${targetBranch}"

                    bat """
                        git config user.name "Sridhar-Ghr"
                        git config user.email "sridhar.cisms@gmail.com"
                        git fetch origin
                        git checkout ${targetBranch}
                        git merge origin/${sourceBranch}
                        git push origin ${targetBranch}
                    """
                }
            }
        }
    }
}