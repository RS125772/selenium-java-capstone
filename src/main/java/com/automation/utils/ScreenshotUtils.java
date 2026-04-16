package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "reports/screenshots/" + testName + ".png";

            FileHandler.copy(src, new File(path));

            return path;

        } catch (Exception e) {
            return null;
        }
    }
}