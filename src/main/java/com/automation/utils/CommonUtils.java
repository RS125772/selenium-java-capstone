package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class CommonUtils {

    private WebDriver driver;

    public CommonUtils(WebDriver driver) {
        this.driver = driver;
    }

    // ================= SCREENSHOT =================
    public void takeScreenshot(String fileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueName = fileName + "_" + Thread.currentThread().getId() + "_" + timestamp;
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("reports/screenshots/" + uniqueName + ".png");
        dest.getParentFile().mkdirs();
        try {
            FileHandler.copy(src, dest);
            // Attach screenshot to Extent report with correct relative path
            String relativePath = "screenshots/" + uniqueName + ".png";
            ExtentTestManager.attachScreenshot(relativePath, "Screenshot: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================= JAVASCRIPT =================
    public void clickByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ================= ALERT =================
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // ================= WINDOW =================
    public void switchToNewWindow() {
        String parent = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
            }
        }
    }

    public void switchToParentWindow(String parent) {
        driver.switchTo().window(parent);
    }

    // ================= ACTIONS =================
    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }

}