// ================= HELPER METHOD =================
def getTestSummary() {
    def pass = 0
    def fail = 0
    def skip = 0

    try {
        def reports = findFiles(glob: '**/surefire-reports/testng-results.xml')

        if (reports.length > 0) {
            def xml = new XmlSlurper().parseText(readFile(reports[0].path))

            pass = xml.@passed.toInteger()
            fail = xml.@failed.toInteger()
            skip = xml.@skipped.toInteger()
        }
    } catch (Exception e) {
        echo "Unable to read test summary: ${e.getMessage()}"
    }

    def total = pass + fail + skip

    return [pass: pass, fail: fail, skip: skip, total: total]
}


// ================= PIPELINE =================
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
            script {

                def summary = getTestSummary()

                emailext(
                    subject: "Automation Report - ${currentBuild.currentResult}",
                    body: """
                    <html>
                    <body style="font-family: Arial;">

                        <h2>Automation Execution Report</h2>

                        <h3 style="color:${currentBuild.currentResult == 'SUCCESS' ? 'green' : 'red'};">
                            Status: ${currentBuild.currentResult}
                        </h3>

                        <table border="1" cellpadding="10" cellspacing="0" style="border-collapse:collapse;">
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

                    </body>
                    </html>
                    """,
                    to: "rahul.k4@zensar.com,rachit.saurabh@zensar.com",
                    mimeType: 'text/html',
                    attachmentsPattern: "${REPORT_PATH}"
                )
            }
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