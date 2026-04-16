package com.automation.utils;

import com.aventstack.extentreports.ExtentTest;

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
}
