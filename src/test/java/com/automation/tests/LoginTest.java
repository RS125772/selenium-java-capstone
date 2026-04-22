package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.utils.CommonUtils;

public class LoginTest extends BaseTest {

    @Test(description = "Valid Login Test", groups = {"smoke"})
    public void verifyValidLogin() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();

        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        loginPage.login(testData.getProperty("validUsername"), testData.getProperty("validPassword"));

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");

        commonUtils.takeScreenshot("ValidLoginTest");
    }

    @Test(description = "Invalid Login Test", groups = {"smoke"})
    public void verifyInvalidLogin() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();

        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        loginPage.login(testData.getProperty("invalidUsername"), testData.getProperty("invalidPassword"));

        String error = loginPage.getErrorMessage();

        Assert.assertTrue(error.contains("Login was unsuccessful"), "Error message not displayed!");

        commonUtils.takeScreenshot("InvalidLoginTest");

    }

@Test(description = "Verify Logout functionality", groups = {"smoke"})
    public void verifyLogoutFunctionality() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        // Step 1: Navigate to Login
        homePage.clickOnLogin();

        // Step 2: Login
        loginPage.login(testData.getProperty("validUsername"), testData.getProperty("validPassword"));

        // Step 3: Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");

        // Step 4: Logout
        loginPage.clickLogout();

        // Step 5: Verify logout
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout failed!");

        // Step 6: Screenshot
        commonUtils.takeScreenshot("LogoutTest");
    }
}