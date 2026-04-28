pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    environment {
        REPORT_PATH = 'reports/ExtentReport.html'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'feature_rachit13042026',
                    url: 'https://github.com/RS125772/selenium-java-capstone.git'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    try {
                        bat 'mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=chrome-headless'
                    } catch (Exception e) {
                        echo "Tests failed, continuing pipeline..."
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/surefire-reports/*.xml'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Test Report'
                ])
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'reports/**', fingerprint: true
            }
        }
    }

    post {
        always {

            archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true

            emailext(
                subject: "Automation Report - ${currentBuild.currentResult}",
                body: """
                <h2>Automation Execution Report</h2>
                <p><b>Status:</b> ${currentBuild.currentResult}</p>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> ${env.BUILD_NUMBER}</p>

                <p>
                <a href="${env.BUILD_URL}artifact/${REPORT_PATH}">
                View Extent Report
                </a>
                </p>
                """,
                to: "rahul.k4@zensar.com,rachit.saurabh@zensar.com",
                mimeType: 'text/html',
                attachmentsPattern: "${REPORT_PATH}"
            )
        }

        success {
            echo 'Pipeline executed successfully!'
        }

        unstable {
            echo 'Build is unstable (test failures present)'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}