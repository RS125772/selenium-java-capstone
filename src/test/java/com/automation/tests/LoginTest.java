package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid Login Test")
    public void verifyValidLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getProperty("validUsername"),
                config.getProperty("validPassword")
        );

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }

    @Test(description = "Invalid Login Test")
    public void verifyInvalidLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getProperty("invalidUsername"),
                config.getProperty("invalidPassword")
        );

        String error = loginPage.getErrorMessage();

        Assert.assertTrue(
                error.contains("Login was unsuccessful"),
                "Error message not displayed!"
        );
    }
}