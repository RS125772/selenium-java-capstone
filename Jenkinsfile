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
                        echo 'Tests failed, continuing pipeline...'
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
            <html>
            <body style="font-family: Arial; background-color:#f4f6f8;">
                <div style="max-width:700px;margin:auto;background:#fff;padding:20px;border-radius:8px;">

                    <h2 style="text-align:center;">Automation Execution Report</h2>

                    <h3 style="color:${currentBuild.currentResult == 'SUCCESS' ? 'green' : 'red'};">
                        Status: ${currentBuild.currentResult}
                    </h3>

                    <table border="1" cellpadding="10" cellspacing="0" width="100%" style="border-collapse:collapse;">
                        <tr>
                            <th>Total</th>
                            <th style="color:green;">Passed</th>
                            <th style="color:red;">Failed</th>
                            <th style="color:orange;">Skipped</th>
                        </tr>
                        <tr align="center">
                            <td>${summary.total}</td>
                            <td>${summary.pass}</td>
                            <td>${summary.fail}</td>
                            <td>${summary.skip}</td>
                        </tr>
                    </table>

                    <br>

                    <p><b>Job:</b> ${env.JOB_NAME}</p>
                    <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

                    <p>
                        <a href="${env.BUILD_URL}artifact/${REPORT_PATH}"
                        style="padding:10px 15px;background:#3498db;color:white;text-decoration:none;border-radius:5px;">
                        View Extent Report
                        </a>
                    </p>

                </div>
            </body>
            </html>
            """,
            to: 'rahul.k4@zensar.com,rachit.saurabh@zensar.com,rachit.saurabh123@gmail.com',
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
