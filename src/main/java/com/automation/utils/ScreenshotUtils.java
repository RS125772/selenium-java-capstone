package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {

        String timestamp = String.valueOf(System.currentTimeMillis());
        String reportPath = "reports/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(reportPath);

            dest.getParentFile().mkdirs(); // create folder if not exists
            FileHandler.copy(src, dest);

            // Return relative path from report HTML location
            return "screenshots/" + testName + "_" + timestamp + ".png";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}