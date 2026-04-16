package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.utils.CommonUtils;

public class LoginTest extends BaseTest {

    @Test(description = "Valid Login Test")
    public void verifyValidLogin() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();

        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        loginPage.login(testData.getProperty("validUsername"),testData.getProperty("validPassword"));

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");

        commonUtils.takeScreenshot("ValidLoginTest");
    }

    @Test(description = "Invalid Login Test")
    public void verifyInvalidLogin() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();

        LoginPage loginPage = new LoginPage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        loginPage.login(testData.getProperty("invalidUsername"),testData.getProperty("invalidPassword"));

        String error = loginPage.getErrorMessage();

        Assert.assertTrue(error.contains("Login was unsuccessful"),"Error message not displayed!");

        commonUtils.takeScreenshot("InvalidLoginTest");
        
    }
}