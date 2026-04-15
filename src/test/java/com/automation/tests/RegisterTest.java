package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test
    public void verifyUserRegistration() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();

        RegisterPage registerPage = new RegisterPage(driver);

        String email = "test" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerUser(testData.getProperty("firstName"), testData.getProperty("lastName"), email, testData.getProperty("password"));

        String message = registerPage.getSuccessMessage();

        Assert.assertTrue(message.contains("Your registration completed"));
    }
}