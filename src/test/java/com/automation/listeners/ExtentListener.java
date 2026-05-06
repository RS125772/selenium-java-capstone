package com.automation.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.drivers.DriverFactory;
import com.automation.utils.ExtentManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ExtentListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Execution started : " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.setTest(extent.createTest(testName));
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTestManager.getTest()
                .log(Status.PASS, "Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest()
                .log(Status.FAIL, result.getThrowable());
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            return;
        }
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                driver,
                result.getMethod().getMethodName());
        if (screenshotPath == null) {
            return;
        }
        try {
            ExtentTestManager.getTest().fail(
                    "Failure screenshot",
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(screenshotPath)
                            .build());

        } catch (Exception e) {

            System.out.println("Unable to attach screenshot to Extent Report");
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTestManager.getTest()
                .log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = context.getAllTestMethods().length;

        System.out.println("\n========== Execution Summary ==========");
        System.out.println("Total Tests : " + total);
        System.out.println("Passed      : " + passed);
        System.out.println("Failed      : " + failed);
        System.out.println("Skipped     : " + skipped);
        System.out.println("=======================================\n");

        extent.flush();
    }
}