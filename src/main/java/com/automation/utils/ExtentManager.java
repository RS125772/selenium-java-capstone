package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    // Single shared ExtentReports instance (Singleton)
    private static ExtentReports extent;

    // Method to get ExtentReports instance
    public static ExtentReports getInstance() {

        // Initialize only once
        if (extent == null) {

            // Create Spark reporter (HTML report path)
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");

            // Set report name (shown in report UI)
            spark.config().setReportName("Demo Web Shop Automation Test Report");

            // Set browser tab title
            spark.config().setDocumentTitle("Demo Web Shop Selenium Test Results");

            // Create ExtentReports object
            extent = new ExtentReports();

            // Attach Spark reporter to ExtentReports
            extent.attachReporter(spark);

            // Add system/environment details to report
            extent.setSystemInfo("Testers", "Rachit, Rahul, Vinay, Sahil");
            extent.setSystemInfo("Environment", "Test");
        }

        // Return existing instance
        return extent;
    }
}