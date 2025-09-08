pipeline {
    agent any

    environment {
        // Project workspace folder
        PROJECT_DIR = "${env.WORKSPACE}"
        // Day-wise screenshot folder
        DATE = new Date().format("yyyy-MM-dd")
        SCREENSHOT_DIR = "${env.WORKSPACE}/Screenshots/${DATE}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/allammaheshbabu/test_automation_pratice_repo.git'
            }
        }

        stage('Setup Screenshot Folder') {
            steps {
                script {
                    // Create day-wise folder
                    sh "mkdir -p '${SCREENSHOT_DIR}'"
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Run Maven tests
                    sh "mvn clean test"
                }
            }
        }

        stage('Publish Reports') {
            steps {
                // Optional: archive screenshots
                archiveArtifacts artifacts: "Screenshots/**", fingerprint: true

                // Optional: publish Cucumber reports (requires plugin)
                cucumberReports canComputeNew: false,
                               fileIncludePattern: 'target/cucumber-reports/*.json',
                               jsonReportDirectory: 'target/cucumber-reports',
                               pluginUrlPath: '',
                               skippedFails: false,
                               pendingFails: false
            }
        }
    }

    post {
        always {
            echo "Build finished. Screenshots stored at: ${SCREENSHOT_DIR}"
        }
    }
}
