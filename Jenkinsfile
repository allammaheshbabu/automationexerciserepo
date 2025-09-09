pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        GIT_CREDENTIALS_ID = 'git-credentials' // Replace with your Jenkins credentials ID
        BRANCH_NAME = 'main'
        ECLIPSE_WORKSPACE = 'C:\\Users\\allam\\eclipse-workspace\\Eclipse Workspace' 
        COMMIT_MESSAGE = 'Automated commit from Jenkins'
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        stage('Checkout from Git') {
            steps {
                echo "Checking out branch ${env.BRANCH_NAME}..."
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${env.BRANCH_NAME}"]],
                    userRemoteConfigs: [[
                        url: 'https://github.com/allammaheshbabu/automationexerciserepo.git', // Replace with your repo
                        credentialsId: "${env.GIT_CREDENTIALS_ID}"
                    ]]
                ])
            }
        }

        stage('Copy Files from Eclipse Workspace') {
            steps {
                bat """
                echo Copying files from Eclipse workspace...
                xcopy /E /Y /I "${ECLIPSE_WORKSPACE}\\*" "."
                """
            }
        }

        stage('Configure Git') {
            steps {
                bat """
                git config user.email "allammaheshbabu232003@gmail.com"
                git config user.name "Mahesh Babu Allam"
                """
            }
        }

        stage('Commit & Push Changes') {
            steps {
                bat """
                git add .

                REM Check if there are changes before committing
                git diff --cached --quiet
                IF %ERRORLEVEL% NEQ 0 (
                    echo Changes detected, committing...
                    git commit -m "${COMMIT_MESSAGE}"
                    git push origin ${BRANCH_NAME}
                ) ELSE (
                    echo No changes to commit.
                )
                """
            }
        }

        stage('Build') {
            steps {
                echo 'Building project using Maven...'
                bat 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running TestNG tests...'
                bat 'mvn test'
            }
        }

        stage('Archive Reports') {
            steps {
                echo 'Archiving TestNG reports...'
                archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
                publishHTML(target: [
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'TestNG HTML Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }
    }

    post {
        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
        success {
            echo '✅ Build, Test, and Git push completed successfully!'
        }
        failure {
            echo '❌ Build or tests failed!'
        }
    }
}
