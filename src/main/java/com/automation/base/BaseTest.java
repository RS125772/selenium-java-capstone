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

    // Config and test data readers instances
    protected ConfigReader config;
    protected TestDataReader testData;

    // ================= SETUP =================

    @BeforeMethod
    public void setUp(Method method) {

        // Initialize config and test data readers to load properties files
        config = new ConfigReader();
        testData = new TestDataReader();

        // Get browser from System property (allow override via command line) - e.g. -Dbrowser=chrome
        String browser = System.getProperty("browser");

        // Fallback to config file if not provided via system property
        if (browser == null || browser.isEmpty()) {
            browser = config.getProperty("browser");
        }

        // Get headless mode from system property   (e.g. -Dheadless=true) to allow running tests without UI in CI/CD environments
        String headless = System.getProperty("headless");

        // Fallback to config (default false)
        if (headless == null || headless.isEmpty()) {
            headless = config.getProperty("headless");
        }

        // Convert to headless browser if required
        if (headless.equalsIgnoreCase("true") && "chrome".equalsIgnoreCase(browser)) {
            browser = "chrome-headless";
        }

        // Initialize WebDriver using factory method based on browser type
        DriverFactory.initDriver(browser);

        // Get driver instance for current thread
        driver = DriverFactory.getDriver();

        // Launch application URL from config file
        driver.get(config.getProperty("url"));

        // Print thread ID (useful for parallel execution) to verify that each test is running in its own thread with separate driver instance
        System.out.println("Thread ID: " + Thread.currentThread().getId());

        // ================= LOGIN CONTROL =================

        // Check if method has @Test annotation and belongs to "requiresLogin" group → if yes, perform login before test execution
        if (method.isAnnotationPresent(Test.class)) {

            // Get @Test annotation details to check groups
            Test test = method.getAnnotation(Test.class);

            // If test belongs to "requiresLogin" group → perform login before test execution
            if (Arrays.asList(test.groups()).contains("requiresLogin")) {
                loginToApplication();
            }
        }
    }

    // ================= LOGIN METHOD =================

    protected void loginToApplication() {

        // Initialize page objects for login flow
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Click login button on home page to navigate to login page
        homePage.clickOnLogin();

        // Perform login using test data from properties file (valid username and password)
        loginPage.login(
                TestDataReader.getProperty("validUsername"),
                TestDataReader.getProperty("validPassword"));

        // Validate login success by checking presence of logout button or user profile element (you can implement isLoginSuccessful() method in LoginPage to check this)
        if (!loginPage.isLoginSuccessful()) {
            throw new RuntimeException("Login failed in setup!");
        }
    }

    // ================= TEARDOWN =================

    @AfterMethod
    public void tearDown() {

        // Quit driver after test execution to close browser and clean up resources
        DriverFactory.quitDriver();
    }
}