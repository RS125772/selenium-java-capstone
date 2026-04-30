package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import com.automation.config.ConfigReader;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    // Global timeout value loaded from config
    private static final int TIMEOUT;

    static {
        // Read timeout from config file
        ConfigReader config = new ConfigReader();
        TIMEOUT = config.getIntProperty("timeout");
    }

    // Create WebDriverWait with configured timeout
    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    // ================= BASIC ELEMENT WAITS =================

    // Wait until element is visible on page
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return waitForVisibility(driver, locator, 3);
    }

    // Wait until element is visible with retry logic for stale elements
    public static WebElement waitForVisibility(WebDriver driver, By locator, int maxRetries) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                if (attempts == maxRetries) {
                    throw e;
                }
                // Wait before retry
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }

    // Wait until element is clickable
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return waitForClickable(driver, locator, 3);
    }

    // Wait until element is clickable with retry logic
    public static WebElement waitForClickable(WebDriver driver, By locator, int maxRetries) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                if (attempts == maxRetries) {
                    throw e;
                }
                // Wait before retry
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }

    // Wait until element is present in DOM
    public static boolean waitForPresence(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
    }

    // Wait until page is fully loaded (document ready state)
    public static void waitForPageToLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
            webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete")
        );
    }

    // ================= COMMON SCENARIOS =================

    // Wait and return success message text
    public static String waitForSuccessMessage(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    // Wait and return error message text
    public static String waitForErrorMessage(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    // Wait for loader/spinner to disappear
    public static void waitForLoaderToDisappear(WebDriver driver, By loader) {
        wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    // Wait until page title contains expected text
    public static boolean waitForTitleContains(WebDriver driver, String title) {
        return wait(driver).until(ExpectedConditions.titleContains(title));
    }

    // Wait until URL contains expected value
    public static boolean waitForUrlContains(WebDriver driver, String fraction) {
        return wait(driver).until(ExpectedConditions.urlContains(fraction));
    }

    // ================= DROPDOWN & ACTIONS =================

    // Wait for dropdown and select value by visible text
    public static void waitForDropdownAndSelect(WebDriver driver, By locator, String value) {
        WebElement dropdown = waitForVisibility(driver, locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    // Click element with retry (handles stale element issues)
    public static void clickWhenReady(WebDriver driver, By locator) {
        int attempts = 0;
        int maxRetries = 3;

        while (attempts < maxRetries) {
            try {
                WebElement element = waitForClickable(driver, locator);
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == maxRetries) {
                    throw new RuntimeException("Failed to click element after " + maxRetries + " retries: " + e.getMessage(), e);
                }
                // Wait before retry to allow DOM to stabilize
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (NoSuchElementException e) {
                attempts++;
                if (attempts == maxRetries) {
                    throw new RuntimeException("Element not found after " + maxRetries + " retries: " + e.getMessage(), e);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // ================= LIST / GRID =================

    // Wait for list of elements to be visible
    public static List<WebElement> waitForProductList(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ================= ALERT =================

    // Wait for alert to be present
    public static Alert waitForAlert(WebDriver driver) {
        return wait(driver).until(ExpectedConditions.alertIsPresent());
    }
}