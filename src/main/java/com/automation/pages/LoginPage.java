package com.automation.pages;

import com.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.automation.utils.ExtentTestManager;

public class LoginPage {

    private WebDriver driver;

    // ================= LOCATORS =================

    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By loginButton = By.cssSelector("input.login-button");
    private By logoutLink = By.className("ico-logout");
    private By errorMessage = By.cssSelector(".validation-summary-errors");

    // ================= CONSTRUCTOR =================

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= ACTIONS =================

    public void login(String email, String password) {
        ExtentTestManager.logInfo("Logging in with email: " + email);
        WaitUtils.waitForVisibility(driver, emailField).sendKeys(email);
        ExtentTestManager.logInfo("Entered email: " + email);
        WaitUtils.waitForVisibility(driver, passwordField).sendKeys(password);
        ExtentTestManager.logInfo("Entered password");
        WaitUtils.waitForClickable(driver, loginButton).click();
        ExtentTestManager.logInfo("Clicked on Login button");
    }

    // ================= VALIDATIONS =================

    public boolean isLoginSuccessful() {
        return WaitUtils.waitForVisibility(driver, logoutLink).isDisplayed();
    }

    public String getErrorMessage() {
        return WaitUtils.waitForVisibility(driver, errorMessage).getText();
    }
}