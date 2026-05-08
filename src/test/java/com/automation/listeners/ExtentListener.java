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

    @Override //This method executes before test suite execution starts. Used to log execution start information.
    public void onStart(ITestContext context) {
        System.out.println("Execution started : " + context.getName());
    }

    @Override //This method executes before every test method starts. Creates a new Extent Test entry for current test case.
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.setTest(extent.createTest(testName));
    }

    @Override //This method executes when test case passes successfully. Updates PASS status in Extent Report.
    public void onTestSuccess(ITestResult result) {

        ExtentTestManager.getTest()
                .log(Status.PASS, "Test passed successfully");
    }

    @Override //This method executes when test case fails. Captures failure reason and attaches screenshot into Extent Report for debugging.
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

    @Override //This method executes when test case is skipped. Updates SKIP status in Extent Report.
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