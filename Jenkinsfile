// ================= HELPER METHOD =================
def getTestSummary() {
    def pass = 0
    def fail = 0
    def skip = 0

    def paths = [
        'target/surefire-reports/testng-results.xml',
        'target/test-output/testng-results.xml'
    ]

    try {
        def found = false

        for (p in paths) {
            if (fileExists(p)) {
                echo "✅ Found test result file: ${p}"
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
            echo "❌ No valid testng-results.xml found"
        }

    } catch (Exception e) {
        echo "❌ Error parsing test results: ${e.getMessage()}"
    }

    def total = pass + fail + skip

    echo "📊 FINAL SUMMARY => Total: ${total}, Passed: ${pass}, Failed: ${fail}, Skipped: ${skip}"

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

        // ✅ NEW: PIE CHART (SAFE, NON-BREAKING)
        stage('Generate Pie Chart') {
            steps {
                script {
                    try {
                        def summary = getTestSummary()

                        bat "if not exist reports mkdir reports"

                        def chartUrl = "https://quickchart.io/chart?c=" +
                            URLEncoder.encode("""
                            {
                              type: 'pie',
                              data: {
                                labels: ['Passed', 'Failed', 'Skipped'],
                                datasets: [{
                                  data: [${summary.pass}, ${summary.fail}, ${summary.skip}],
                                  backgroundColor: ['green','red','orange']
                                }]
                              }
                            }
                            """, "UTF-8")

                        bat "curl -o reports/piechart.png \"${chartUrl}\""

                        echo "✅ Pie chart generated"

                    } catch (Exception e) {
                        echo "⚠️ Pie chart generation failed: ${e.getMessage()}"
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

                def statusColor = currentBuild.currentResult == 'SUCCESS' ? 'green' :
                                  currentBuild.currentResult == 'UNSTABLE' ? 'orange' : 'red'

                emailext(
                    subject: "Demo Web Shop Automation Report - ${currentBuild.currentResult}",
                    body: """
                    <html>
                    <body style="font-family: Arial; background-color:#f4f6f8;">
                        <div style="max-width:700px;margin:auto;background:#fff;padding:20px;border-radius:8px;">

                            <h2 style="text-align:center;">Automation Execution Report</h2>

                            <div style="text-align:center;">
                                <img src="cid:piechart.png" width="300"/>
                            </div>

                            <h3 style="color:${statusColor};">
                                Status: ${currentBuild.currentResult}
                            </h3>

                            <table border="1" cellpadding="10" cellspacing="0" width="100%">
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

                            <div style="text-align:center;">
                                <a href="${env.BUILD_URL}artifact/${REPORT_PATH}"
                                style="padding:10px 15px;background:#3498db;color:white;text-decoration:none;border-radius:5px;">
                                View Extent Report
                                </a>
                            </div>

                        </div>
                    </body>
                    </html>
                    """,
                    to: "rahul.k4@zensar.com,rachit.saurabh@zensar.com",
                    mimeType: 'text/html',
                    attachmentsPattern: "${REPORT_PATH}, reports/piechart.png"
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