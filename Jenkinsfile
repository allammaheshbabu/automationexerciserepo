pipeline {
    agent any

    tools {
        // Make sure JDK21 and Maven are installed in Jenkins
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning GitHub repository...'
                git url: 'https://github.com/allammaheshbabu/automationexerciserepo.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo 'Building project using Maven...'
                bat 'mvn clean compile'
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
            echo 'Build & Tests successful!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}
