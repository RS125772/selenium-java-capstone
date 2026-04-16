package com.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.automation.utils.ExtentManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.ScreenshotUtils;
import com.automation.drivers.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentListener implements ITestListener {

    private ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.setTest(
                extent.createTest(result.getMethod().getMethodName())
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());

        String screenshotPath = ScreenshotUtils.captureScreenshot(
                DriverFactory.getDriver(),
                result.getMethod().getMethodName()
        );

        if (screenshotPath != null) {
            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}