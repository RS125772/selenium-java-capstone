package com.automation.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ExtentTestManager {

    // ThreadLocal to store separate ExtentTest for each thread (parallel execution)
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Set ExtentTest instance for current thread
    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    // Get ExtentTest instance for current thread
    public static ExtentTest getTest() {
        return test.get();
    }

    // Log informational message to report
    public static void logInfo(String message) {

        if (getTest() != null) {
            getTest().info(message); // Log info in Extent report
        } else {
            // Fallback if ExtentTest not initialized
            System.out.println("ExtentTest is NULL → " + message);
        }
    }

    /**
     * Attach screenshot to Extent report
     * @param screenshotPath Path of screenshot file
     * @param message Description for screenshot
     */
    public static void attachScreenshot(String screenshotPath, String message) {

        if (getTest() != null) {
            try {
                // Attach screenshot with info log
                getTest().info(
                        message,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
                );
            } catch (Exception e) {
                // Handle failure in attaching screenshot
                System.out.println("Failed to attach screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ExtentTest is NULL → Cannot attach screenshot");
        }
    }

    /**
     * Mark test as passed with screenshot
     * @param screenshotPath Path of screenshot file
     * @param message Description message
     */
    public static void passWithScreenshot(String screenshotPath, String message) {

        if (getTest() != null) {
            try {
                // Mark step as pass with screenshot
                getTest().pass(
                        message,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
                );
            } catch (Exception e) {
                // Handle failure in attaching screenshot
                System.out.println("Failed to attach screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ExtentTest is NULL → Cannot attach screenshot");
        }
    }
}