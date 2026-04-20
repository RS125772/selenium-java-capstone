package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.config.TestDataReader;
import com.automation.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver; // WebDriver instance used across test classes
    protected ConfigReader config; // Object to read configuration properties (like: browser, URL, etc.)
    protected TestDataReader testData; // Object to read test data (like: username, password, etc.)

    // This method runs before every test method
    @BeforeMethod
    public void setUp() {

        config = new ConfigReader(); // Initialize config reader to load environment-related properties
        testData = new TestDataReader(); // Initialize test data reader to load test-specific data

        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = config.getProperty("browser"); // Fetch browser name from config.properties (e.g., chrome, edge)
        }

        String headless = System.getProperty("headless");
        if (headless == null || headless.isEmpty()) {
            headless = config.getProperty("headless", "false");
        }

        if (headless.equalsIgnoreCase("true") && "chrome".equalsIgnoreCase(browser)) {
            browser = "chrome-headless";
        }

        // Initialize driver based on the specified browser type in the configuration
        DriverFactory.initDriver(browser);

        // Get the thread-safe WebDriver instance from DriverFactory
        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();
        driver.get(config.getProperty("url")); // Launch application URL from config file

        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    // This method runs after every test method
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver(); // Quit browser and remove driver instance from ThreadLocal
    }
}