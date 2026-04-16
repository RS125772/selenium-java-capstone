package com.automation.pages;

import com.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        WaitUtils.waitForVisibility(driver, emailField).sendKeys(email);
        WaitUtils.waitForVisibility(driver, passwordField).sendKeys(password);
        WaitUtils.waitForClickable(driver, loginButton).click();
    }

    // ================= VALIDATIONS =================

    public boolean isLoginSuccessful() {
        return WaitUtils.waitForVisibility(driver, logoutLink).isDisplayed();
    }

    public String getErrorMessage() {
        return WaitUtils.waitForVisibility(driver, errorMessage).getText();
    }
}