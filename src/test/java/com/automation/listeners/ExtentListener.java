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

    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        ExtentTestManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            // Take screenshot
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
                if (screenshotPath != null) {
                    try {
                        test.fail("Screenshot on failure", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    @Override
    public void onStart(ITestContext context) {
        // Optional: Log suite start
        System.out.println("Starting Test Suite: " + context.getName());
    }

    @Override
public void onFinish(ITestContext context) {

    int passed = context.getPassedTests().size();
    int failed = context.getFailedTests().size();
    int skipped = context.getSkippedTests().size();
    int total = context.getAllTestMethods().length;

    System.out.println("\n================ TEST EXECUTION SUMMARY ================");
    System.out.println("TOTAL TESTS : " + total);
    System.out.println("PASSED      : " + passed);
    System.out.println("FAILED      : " + failed);
    System.out.println("SKIPPED     : " + skipped);
    System.out.println("=======================================================\n");

    extent.flush();
}
}