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

        config = new ConfigReader();
        testData = new TestDataReader();

        String browser = config.getProperty("browser");

        // Initialize driver
        DriverFactory.initDriver(browser);

        // Assign thread-safe driver to instance variable
        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();
        driver.get(config.getProperty("url"));

        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}