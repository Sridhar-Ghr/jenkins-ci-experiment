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
                    // Determine source and target branches
                    def sourceBranch = env.BRANCH_NAME ?: env.GIT_BRANCH

                    if (!sourceBranch || sourceBranch == 'HEAD') {
                        def ref = bat(script: 'git symbolic-ref --short HEAD || git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                        sourceBranch = ref
                    }

                    sourceBranch = sourceBranch.replaceFirst(/^origin\//, '')
                    def targetBranch = 'main'

                    echo "Merging ${sourceBranch} into ${targetBranch}"

                    // Securely use GitHub credentials stored in Jenkins
                    withCredentials([usernamePassword(credentialsId: '91b02c51-091d-477a-a64e-f110655c53ed', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_TOKEN')]) {
                        bat """
                            git config user.name "Sridhar-Ghr"
                            git config user.email "sridhar.cisms@gmail.com"

                            echo Fetching latest changes...
                            git fetch origin

                            echo Checking out ${targetBranch}...
                            git checkout ${targetBranch}

                            echo Merging ${sourceBranch} into ${targetBranch}...
                            git merge origin/${sourceBranch}

                            echo Setting authenticated remote URL...
                            git remote set-url origin https://%GIT_USER%:%GIT_TOKEN%@github.com/Sridhar-Ghr/jenkins-ci-experiment.git

                            echo Pushing merged changes to ${targetBranch}...
                            git push origin ${targetBranch}
                        """
                    }
                }
            }
        }
    }
}