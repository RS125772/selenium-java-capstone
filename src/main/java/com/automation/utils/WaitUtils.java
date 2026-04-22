package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import com.automation.config.ConfigReader;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private static final int TIMEOUT;

    static {
        ConfigReader config = new ConfigReader();
        TIMEOUT = config.getIntProperty("timeout");
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    // ================= BASIC ELEMENT WAITS =================

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForPresence(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
    }

    // ================= COMMON NOPCOMMERCE SCENARIOS =================

    // 1. Wait for success message (after add to cart / registration)
    public static String waitForSuccessMessage(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    // 2. Wait for error message
    public static String waitForErrorMessage(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    // 3. Wait for loading overlay to disappear (used in cart/checkout)
    public static void waitForLoaderToDisappear(WebDriver driver, By loader) {
        wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    // 4. Wait for page title (useful for navigation validation)
    public static boolean waitForTitleContains(WebDriver driver, String title) {
        return wait(driver).until(ExpectedConditions.titleContains(title));
    }

    // 5. Wait for URL change (login, checkout steps)
    public static boolean waitForUrlContains(WebDriver driver, String fraction) {
        return wait(driver).until(ExpectedConditions.urlContains(fraction));
    }

    // ================= DROPDOWN & FILTERS =================

    public static void waitForDropdownAndSelect(WebDriver driver, By locator, String value) {
        WebElement dropdown = waitForVisibility(driver, locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    public static void clickWhenReady(WebDriver driver, By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForClickable(driver, locator).click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) {
                    throw e;
                }
            }
        }
    }

    // ================= LIST / PRODUCT GRID =================

    public static List<WebElement> waitForProductList(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ================= FRAME / ALERT =================

    public static Alert waitForAlert(WebDriver driver) {
        return wait(driver).until(ExpectedConditions.alertIsPresent());
    }
}