package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.utils.RetryAnalyzer;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.utils.CommonUtils;

public class LoginTest extends BaseTest {

    @Test(description = "Valid Login Test", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyValidLogin() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);
        loginPage.login(testData.getProperty("validUsername"), testData.getProperty("validPassword"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
        commonUtils.takeScreenshot("ValidLoginTest");
    }

    @Test(description = "Invalid Login Test", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
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

    @Test(description = "Verify Logout functionality", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyLogoutFunctionality() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);
        homePage.clickOnLogin();
        loginPage.login(testData.getProperty("validUsername"), testData.getProperty("validPassword"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
        loginPage.clickLogout();
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout failed!");
        commonUtils.takeScreenshot("LogoutTest");
    }

    @Test(
        description = "Verify Forgot Password functionality",
        groups = { "smoke" },
        retryAnalyzer = RetryAnalyzer.class
)
public void verifyForgotPasswordFunctionality() {
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = new LoginPage(driver);
    CommonUtils commonUtils = new CommonUtils(driver);
    homePage.clickOnLogin();
    loginPage.clickForgotPassword();
    loginPage.recoverPassword(testData.getProperty("forgotPasswordEmail"));
    Assert.assertTrue(loginPage.isRecoverPasswordMessageDisplayed(),"Forgot password success message not displayed");
    Assert.assertEquals(loginPage.getRecoverPasswordMessage(),"Email with instructions has been sent to you.","Forgot password confirmation message mismatch");
    commonUtils.takeScreenshot("ForgotPasswordTest");
    }
}