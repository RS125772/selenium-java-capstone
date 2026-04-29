// ================= HELPER METHOD =================
def getTestSummary() {
    def pass = 0
    def fail = 0
    def skip = 0

    def paths = ['target/surefire-reports/testng-results.xml','target/test-output/testng-results.xml']

    try {
        def found = false

        for (p in paths) {
            if (fileExists(p)) {
                echo "Found test result file: ${p}"
                def content = readFile(p)

                def passMatch = (content =~ /passed="(\d+)"/)
                def failMatch = (content =~ /failed="(\d+)"/)
                def skipMatch = (content =~ /skipped="(\d+)"/)

                if (passMatch && failMatch && skipMatch) {
                    pass = passMatch[0][1].toInteger()
                    fail = failMatch[0][1].toInteger()
                    skip = skipMatch[0][1].toInteger()
                    found = true
                    break
                }
            }
        }

        if (!found) {
            echo 'No valid testng-results.xml found'
        }
    } catch (Exception e) {
        echo "Error parsing test results: ${e.getMessage()}"
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
                        echo 'Tests failed, marking build as UNSTABLE...'
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
                def passPercent = summary.total > 0 ? String.format("%.2f", (summary.pass * 100.0 / summary.total)) : "0.00"
                def duration = currentBuild.durationString.replace(" and counting", "")

                def statusColor = currentBuild.currentResult == 'SUCCESS' ? '#2ecc71' :
                currentBuild.currentResult == 'UNSTABLE' ? '#f39c12' : '#e74c3c'

                emailext(
                    subject: "Demo Web Shop Automation Report - ${currentBuild.currentResult}",
                    body: """
                    <html>
                    <body style="font-family: Arial; background:#f4f6f8;">

                    <div style="max-width:700px;margin:auto;background:white;padding:20px;border-radius:10px;">

                    <h2 style="text-align:center;">Automation Execution Report</h2>

                    <!-- STATUS BADGE -->
                    <div style="text-align:center;margin:15px;">
                    <span style="padding:10px 20px;background:${statusColor};color:white;border-radius:20px;font-weight:bold;">
                    ${currentBuild.currentResult}
                    </span>
                    </div>

                    <!-- SUMMARY CARDS -->
                    <table width="100%" style="text-align:center;">
                    <tr>
                        <td style="background:#ecf0f1;padding:10px;border-radius:8px;">
                            <b>Total</b><br>${summary.total}
                        </td>

                        <td style="background:#d4efdf;padding:10px;border-radius:8px;">
                            <b style="color:green;">Passed</b><br>${summary.pass}
                        </td>

                        <td style="background:#f5b7b1;padding:10px;border-radius:8px;">
                            <b style="color:red;">Failed</b><br>${summary.fail}
                        </td>

                        <td style="background:#fdebd0;padding:10px;border-radius:8px;">
                            <b style="color:orange;">Skipped</b><br>${summary.skip}
                        </td>
                    </tr>
                    </table>

                    <!-- PASS % -->
                    <div style="margin-top:20px;">
                        <b>Pass Percentage:</b> ${passPercent}%
                    </div>

                    <!-- PROGRESS BAR -->
                    <div style="background:#ddd;border-radius:10px;margin-top:10px;">
                        <div style="width:${passPercent}%;background:#2ecc71;color:white;
                        padding:5px;border-radius:10px;text-align:center;">
                        ${passPercent}%
                        </div>
                    </div>

                    <!-- DETAILS -->
                    <div style="margin-top:20px;">
                        <p><b>Job:</b> ${env.JOB_NAME}</p>
                        <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                        <p><b>Duration:</b> ${duration}</p>
                    </div>

                    <!-- BUTTON -->
                    <div style="text-align:center;margin-top:25px;">
                        <a href="${env.BUILD_URL}artifact/${REPORT_PATH}"
                        style="padding:12px 20px;background:#3498db;color:white;text-decoration:none;border-radius:6px;">
                        View Detailed Report
                        </a>
                    </div>

                    </div>
                    </body>
                    </html>
                    """,
                    to: 's.mujawar@zensar.com,garlapati.vinay@zensar.com,rahul.k4@zensar.com,rachit.saurabh@zensar.com',
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