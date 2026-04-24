package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtils {

    // Capture screenshot and return relative path for report usage
    public static String captureScreenshot(WebDriver driver, String testName) {

        // Generate unique timestamp for file naming
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Define full file path for storing screenshot
        String reportPath = "reports/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            // Take screenshot and store as temporary file
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Define destination file location
            File dest = new File(reportPath);

            // Create directories if not already present
            dest.getParentFile().mkdirs();

            // Copy screenshot file to destination path
            FileHandler.copy(src, dest);

            // Return relative path (used in Extent Report)
            return "screenshots/" + testName + "_" + timestamp + ".png";

        } catch (Exception e) {
            // Print error if screenshot capture fails
            e.printStackTrace();

            // Return null if failure occurs
            return null;
        }
    }
}