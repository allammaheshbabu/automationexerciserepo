pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:/Program Files/Java/jdk-21' // Update to your JDK path
        PATH = "${env.JAVA_HOME}/bin;${env.PATH}"
        PROJECT_DIR = "C:/Users/allam/eclipse-workspace/Eclipse Workspace/automation-testing-pratice"
        LIB_DIR = "${PROJECT_DIR}/lib"
        BIN_DIR = "${PROJECT_DIR}/bin"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/allammaheshbabu/automationexerciserepo.git'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling Java files...'
                script {
                    bat """
                    cd "${PROJECT_DIR}"
                    if not exist bin mkdir bin
                    for /R src %%f in (*.java) do (
                        javac -d bin -cp "lib/*;bin" "%%f"
                    )
                    """
                }
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running TestNG tests...'
                script {
                    bat """
                    cd "${PROJECT_DIR}"
                    java -cp "bin;lib/*" org.testng.TestNG testng.xml
                    """
                }
            }
        }

        stage('Publish Reports') {
            steps {
                echo 'Copy Extent Reports to Jenkins workspace'
                // Assuming your Extent Reports output path is 'test-output/ExtentReports.html'
                publishHTML(target: [
                    reportDir: "${PROJECT_DIR}/test-output",
                    reportFiles: 'ExtentReports.html',
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            deleteDir()
        }
        failure {
            echo 'Build failed!'
        }
        success {
            echo 'Build succeeded!'
        }
    }
}
