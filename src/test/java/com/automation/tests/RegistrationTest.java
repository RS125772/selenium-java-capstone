package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.CommonUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @Test(description = "Verify successful user registration")
    public void verifyUserRegistration() {

        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();

        RegistrationPage registerPage = new RegistrationPage(driver);

        String email = "test" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerUser(testData.getProperty("firstName"), testData.getProperty("lastName"), email, testData.getProperty("password"));

        String message = registerPage.getSuccessMessage();

        Assert.assertTrue(message.contains("Your registration completed"));

        commonUtils.takeScreenshot("UserRegistrationTest");
    }

    @Test(description = "Verify user registration fails when passwords do not match")
public void verifyInvalidUserRegistration() {

    CommonUtils commonUtils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    homePage.clickOnRegisterButton();

    RegistrationPage registerPage = new RegistrationPage(driver);

    String email = "test" + System.currentTimeMillis() + "@gmail.com";

    registerPage.registerUserWithDifferentPasswords(
            testData.getProperty("firstName"), testData.getProperty("lastName"), 
            email, testData.getProperty("password"), testData.getProperty("confirmPassword")) ;

    Assert.assertTrue(registerPage.getConfirmPasswordError().contains("The password and confirmation password do not match"),
            "Error message not displayed for mismatched passwords"
    );
    commonUtils.takeScreenshot("InvalidUserRegistrationTest");
}
}