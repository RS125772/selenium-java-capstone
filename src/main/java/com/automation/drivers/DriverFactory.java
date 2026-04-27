package com.automation.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.UnexpectedAlertBehaviour;

public class DriverFactory {

    // ThreadLocal to support parallel execution (one driver per thread)
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize WebDriver based on browser name
    public static WebDriver initDriver(String browser) {

        // Switch based on browser type
        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                //FIX FOR ALERT ISSUE
                chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "chrome-headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions headlessOptions = new ChromeOptions();
                headlessOptions.addArguments("--headless=new");
                headlessOptions.addArguments("--window-size=1920,1080");
                driver.set(new ChromeDriver(headlessOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver.set(new EdgeDriver(edgeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver.set(new FirefoxDriver(firefoxOptions));
                driver.get().manage().window().maximize();
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }

        // Return the initialized driver instance for the current thread
        return getDriver();
    }

    // Get current thread's WebDriver instance
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit driver and clean up ThreadLocal
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}