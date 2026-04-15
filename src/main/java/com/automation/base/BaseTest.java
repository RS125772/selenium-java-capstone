package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.config.TestDataReader;
import com.automation.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;
    protected TestDataReader testData;

    @BeforeMethod
    public void setUp() {

        // Load config (environment)
        config = new ConfigReader();

        // Load test data
        testData = new TestDataReader();

        // Get browser from config.properties
        String browser = config.getProperty("browser");

        // Initialize driver
        DriverFactory.initDriver(browser);

        // Get driver from ThreadLocal
        driver = DriverFactory.getDriver();

        // Maximize window (recommended)
        driver.manage().window().maximize();

        // Launch application URL
        driver.get(config.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {

        // Quit driver safely
        DriverFactory.quitDriver();
    }
}