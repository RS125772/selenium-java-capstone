package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.CommonUtils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return new Object[][] {
                { "Rachit", "Saurabh", "Test" + System.currentTimeMillis() + "@gmail.com", "Test@123" }
        };
    }

    @Test(description = "Verify successful user registration", dataProvider = "registrationData", groups = {"regression" })
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

    @Test(description = "Verify user registration fails when passwords do not match", groups = { "regression" })
    public void verifyInvalidUserRegistration() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();
        RegistrationPage registerPage = new RegistrationPage(driver);
        String email = "test" + System.currentTimeMillis() + "@gmail.com";
        registerPage.registerUserWithDifferentPasswords(testData.getProperty("firstName"), testData.getProperty("lastName"),
                email, testData.getProperty("password"), testData.getProperty("confirmPassword"));
        Assert.assertTrue(registerPage.getConfirmPasswordError().contains("The password and confirmation password do not match"),
                "Error message not displayed for mismatched passwords");
        commonUtils.takeScreenshot("InvalidUserRegistrationTest");
    }
}