package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");

            spark.config().setReportName("Demo Web Shop Automation Test Report");
            spark.config().setDocumentTitle("Demo Web ShopSelenium Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System info
            extent.setSystemInfo("Testers", "Rachit, Rahul, Vinay, Sahil");
            extent.setSystemInfo("Environment", "Test");
        }

        return extent;
    }
}