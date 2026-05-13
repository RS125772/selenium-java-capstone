package com.automation.tests;

import com.automation.config.TestDataReader;
import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.CommonUtils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.utils.RetryAnalyzer;

public class RegistrationTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return new Object[][] {{ "Rachit", "Saurabh", "Test" + System.currentTimeMillis() + "@gmail.com", "Test@123" }};
    }

    @Test(description = "Verify successful user registration", dataProvider = "registrationData", groups = {"regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyUserRegistration(String fname, String lname, String email, String pwd) {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();
        RegistrationPage registerPage = new RegistrationPage(driver);
        registerPage.registerUser(fname, lname, email, pwd);
        String message = registerPage.getSuccessMessage();
        Assert.assertTrue(message.contains("Your registration completed"));
        commonUtils.takeScreenshot("UserRegistrationTest");
    }

    @Test(description = "Verify user registration fails when passwords do not match", groups = { "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyInvalidUserRegistration() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();
        RegistrationPage registerPage = new RegistrationPage(driver);
        String email = "test" + System.currentTimeMillis() + "@gmail.com";
        registerPage.registerUserWithDifferentPasswords(TestDataReader.getProperty("firstName"), TestDataReader.getProperty("lastName"),
                email, TestDataReader.getProperty("password"), TestDataReader.getProperty("confirmPassword"));
        Assert.assertTrue(registerPage.getConfirmPasswordError().contains("The password and confirmation password do not match"),
                "Error message not displayed for mismatched passwords");
        commonUtils.takeScreenshot("InvalidUserRegistrationTest");
    }
}