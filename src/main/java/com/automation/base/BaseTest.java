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

    protected WebDriver driver;
    protected ConfigReader config;
    protected TestDataReader testData;

    // ================= SETUP =================

    @BeforeMethod
    public void setUp(Method method) {

        config = new ConfigReader();
        testData = new TestDataReader();

        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = config.getProperty("browser");
        }

        String headless = System.getProperty("headless");
        if (headless == null || headless.isEmpty()) {
            headless = config.getProperty("headless", "false");
        }

        if (headless.equalsIgnoreCase("true") && "chrome".equalsIgnoreCase(browser)) {
            browser = "chrome-headless";
        }

        // Initialize driver
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();

        driver.get(config.getProperty("url"));

        System.out.println("Thread ID: " + Thread.currentThread().getId());

        // ================= LOGIN CONTROL =================

        if (method.isAnnotationPresent(Test.class)) {
            Test test = method.getAnnotation(Test.class);

            if (Arrays.asList(test.groups()).contains("requiresLogin")) {
                loginToApplication();
            }
        }
    }

    // ================= LOGIN METHOD =================

    protected void loginToApplication() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickOnLogin();

        loginPage.login(testData.getProperty("validUsername"),testData.getProperty("validPassword")
        );

        // Validation to ensure login success
        if (!loginPage.isLoginSuccessful()) {
            throw new RuntimeException("Login failed in setup!");
        }
    }

    // ================= TEARDOWN =================

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}