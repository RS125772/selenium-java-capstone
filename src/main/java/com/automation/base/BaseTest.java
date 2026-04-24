package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.config.TestDataReader;
import com.automation.drivers.DriverFactory;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BaseTest {

    // WebDriver instance
    protected WebDriver driver;

    // Config and test data readers
    protected ConfigReader config;
    protected TestDataReader testData;

    // ================= SETUP =================

    @BeforeMethod
    public void setUp(Method method) {

        // Initialize config and test data
        config = new ConfigReader();
        testData = new TestDataReader();

        // Get browser from System property
        String browser = System.getProperty("browser");

        // Fallback to config file if not provided
        if (browser == null || browser.isEmpty()) {
            browser = config.getProperty("browser");
        }

        // Get headless mode from system property
        String headless = System.getProperty("headless");

        // Fallback to config (default false)
        if (headless == null || headless.isEmpty()) {
            headless = config.getProperty("headless");
        }

        // Convert to headless browser if required
        if (headless.equalsIgnoreCase("true") && "chrome".equalsIgnoreCase(browser)) {
            browser = "chrome-headless";
        }

        // Initialize WebDriver using factory
        DriverFactory.initDriver(browser);

        // Get driver instance
        driver = DriverFactory.getDriver();

        // Launch application URL
        driver.get(config.getProperty("url"));

        // Print thread ID (useful for parallel execution)
        System.out.println("Thread ID: " + Thread.currentThread().getId());

        // ================= LOGIN CONTROL =================

        // Check if method has @Test annotation
        if (method.isAnnotationPresent(Test.class)) {

            // Get @Test annotation details
            Test test = method.getAnnotation(Test.class);

            // If test belongs to "requiresLogin" group → perform login
            if (Arrays.asList(test.groups()).contains("requiresLogin")) {
                loginToApplication();
            }
        }
    }

    // ================= LOGIN METHOD =================

    protected void loginToApplication() {

        // Initialize page objects
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Click login button
        homePage.clickOnLogin();

        // Perform login using test data
        loginPage.login(
                testData.getProperty("validUsername"),
                testData.getProperty("validPassword"));

        // Validate login success
        if (!loginPage.isLoginSuccessful()) {
            throw new RuntimeException("Login failed in setup!");
        }
    }

    // ================= TEARDOWN =================

    @AfterMethod
    public void tearDown() {

        // Quit driver after test execution
        DriverFactory.quitDriver();
    }
}