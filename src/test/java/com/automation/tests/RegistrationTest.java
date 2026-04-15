package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @Test
    public void verifyUserRegistration() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnRegisterButton();

        RegistrationPage registerPage = new RegistrationPage(driver);

        String email = "test" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerUser(testData.getProperty("firstName"), testData.getProperty("lastName"), email, testData.getProperty("password"));

        String message = registerPage.getSuccessMessage();

        Assert.assertTrue(message.contains("Your registration completed"));
    }
}