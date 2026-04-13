package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;

    @BeforeMethod
    public void setUp() {

        // Load config
        config = new ConfigReader();

        // Get browser from config.properties
        String browser = config.getProperty("browser");

        // Initialize driver
        driver = DriverFactory.initDriver(browser);

        // Get ThreadLocal driver instance
        driver = DriverFactory.getDriver();

        // Launch application URL from config
        driver.get(config.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {

        // Quit driver safely (ThreadLocal)
        DriverFactory.quitDriver();
    }
}