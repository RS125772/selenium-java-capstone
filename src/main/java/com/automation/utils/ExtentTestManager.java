package com.automation.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ExtentTestManager {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().info(message);
        } else {
            System.out.println("ExtentTest is NULL → " + message);
        }
    }

    /**
     * Attach a screenshot to the Extent report
     * 
     * @param screenshotPath Relative path to the screenshot (e.g.,
     *                       "screenshots/test_123456.png")
     * @param message        Description for the screenshot
     */
    public static void attachScreenshot(String screenshotPath, String message) {
        if (getTest() != null) {
            try {
                getTest().info(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                System.out.println("Failed to attach screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ExtentTest is NULL → Cannot attach screenshot");
        }
    }

    /**
     * Pass a test with screenshot attached
     * 
     * @param screenshotPath Relative path to the screenshot
     * @param message        Description for the screenshot
     */
    public static void passWithScreenshot(String screenshotPath, String message) {
        if (getTest() != null) {
            try {
                getTest().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                System.out.println("Failed to attach screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ExtentTest is NULL → Cannot attach screenshot");
        }
    }
}
