pipeline {
    agent any

    tools {
        jdk 'JDK21' // Make sure this JDK name matches Jenkins configuration
    }

    environment {
        PROJECT_DIR = "${WORKSPACE}" // Jenkins workspace
        BIN_DIR = "${WORKSPACE}/bin"
        SRC_DIR = "${WORKSPACE}/src"
        LIB_DIR = "${WORKSPACE}/lib"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/allammaheshbabu/automationexerciserepo.git'
            }
        }

        stage('Prepare Workspace') {
            steps {
                script {
                    // Create bin folder if not exist
                    if (!fileExists(BIN_DIR)) {
                        sh "mkdir -p ${BIN_DIR}"
                    }
                }
            }
        }

        stage('Compile Java') {
            steps {
                script {
                    echo "Compiling Java files..."
                    // Using Windows cmd if agent is Windows
                    bat """
                    for /R ${SRC_DIR} %%f in (*.java) do (
                        javac -d ${BIN_DIR} -cp "${LIB_DIR}/*;${BIN_DIR}" "%%f"
                    )
                    """
                }
            }
        }

        stage('Run TestNG') {
            steps {
                echo "Running TestNG tests..."
                bat """
                java -cp "${BIN_DIR};${LIB_DIR}/*" org.testng.TestNG testng.xml
                """
            }
        }
    }

    post {
        always {
            echo "Cleaning up workspace..."
        }
        success {
            echo "Build and tests completed successfully!"
        }
        failure {
            echo "Build failed. Check logs for errors."
        }
    }
}
