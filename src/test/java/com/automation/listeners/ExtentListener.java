package com.automation.listeners;

import com.automation.drivers.DriverFactory;
import com.automation.utils.ExtentManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentListener implements ITestListener {

    // Get single ExtentReports instance
    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {

        // Create test entry in report using method name
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());

        // Store test instance in ThreadLocal
        ExtentTestManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        // Get current test instance
        ExtentTest test = ExtentTestManager.getTest();

        if (test != null) {
            // Log test pass status
            test.log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        // Get current test instance
        ExtentTest test = ExtentTestManager.getTest();

        if (test != null) {

            // Log failure with exception details
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

            // Get WebDriver instance
            WebDriver driver = DriverFactory.getDriver();

            if (driver != null) {

                // Capture screenshot on failure
                String screenshotPath = ScreenshotUtils.captureScreenshot(
                        driver,
                        result.getMethod().getMethodName()
                );

                if (screenshotPath != null) {
                    try {
                        // Attach screenshot to report
                        test.fail(
                                "Screenshot on failure",
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
                        );
                    } catch (Exception e) {
                        // Handle screenshot attachment error
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        // Get current test instance
        ExtentTest test = ExtentTestManager.getTest();

        if (test != null) {
            // Log skipped status
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    @Override
    public void onStart(ITestContext context) {

        // Print suite start info
        System.out.println("Starting Test Suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {

        // Get test execution summary
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = context.getAllTestMethods().length;

        // Print execution summary in console
        System.out.println("\n================ TEST EXECUTION SUMMARY ================");
        System.out.println("TOTAL TESTS : " + total);
        System.out.println("PASSED      : " + passed);
        System.out.println("FAILED      : " + failed);
        System.out.println("SKIPPED     : " + skipped);
        System.out.println("=======================================================\n");

        // Flush report to write results
        extent.flush();
    }
}