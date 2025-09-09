pipeline {
    agent any
 
    // Trigger pipeline automatically on Git changes
    triggers {
        pollSCM('H/5 * * * *') // Checks every 5 minutes for changes
    }
 
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/allammaheshbabu/automationexerciserepo.git'
            }
        }
 
        stage('Build & Test') {
            steps {
                bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml'
            }
        }
 
        stage('Commit & Push Changes') {
            steps {
                script {
                    echo 'Checking for changes to push...'
 
                    withCredentials([usernamePassword(
                        credentialsId: 'allammaheshbabu232003', 
                        usernameVariable: 'GIT_USER', 
                        passwordVariable: 'GIT_TOKEN')]) {
 
                        bat """
                            git config user.email "allammaheshbabu232003@gmail.com"
                            git config user.name "allammaheshbabu"
 
                            git status
                            git add .
 
                            REM Commit only if there are changes
                            git diff --cached --quiet || git commit -m "Jenkins: Auto-commit after build"
 
                            REM Push using token
                            git push https://%GIT_USER%:%GIT_TOKEN%@github.com/allammaheshbabu/automationexerciserepo.git main
                        """
                    }
                }
            }
        }
    }
 
    post {
        always {
            // Archive screenshots
            archiveArtifacts artifacts: 'reports/screenshots/*', fingerprint: true
 
            // Publish Cucumber Report
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/cucumber-reports',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber Report'
            ])
 
            // Publish Extent Report
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/extent-reports',
                reportFiles: 'index.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
 