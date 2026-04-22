package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.WaitUtils;

public class RegistrationPage {

    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By genderMale = By.id("gender-male");
    private By firstName = By.id("FirstName");
    private By lastName = By.id("LastName");
    private By email = By.id("Email");
    private By password = By.id("Password");
    private By confirmPassword = By.id("ConfirmPassword");
    private By registerBtn = By.id("register-button");
    private By successMsg = By.className("result");

    private By confirmPasswordError = By.xpath("//span[@data-valmsg-for='ConfirmPassword']");

    // Actions
    public void selectGenderMale() {
        ExtentTestManager.logInfo("Selecting gender: Male");
        WaitUtils.clickWhenReady(driver, genderMale);
    }

    public void enterFirstName(String fname) {
        ExtentTestManager.logInfo("Entering first name: " + fname);
        WaitUtils.waitForVisibility(driver, firstName).sendKeys(fname);
    }

    public void enterLastName(String lname) {
        ExtentTestManager.logInfo("Entering last name: " + lname);
        WaitUtils.waitForVisibility(driver, lastName).sendKeys(lname);
    }

    public void enterEmail(String mail) {
        ExtentTestManager.logInfo("Entering email: " + mail);
        WaitUtils.waitForVisibility(driver, email).sendKeys(mail);
    }

    public void enterPassword(String pwd) {
        ExtentTestManager.logInfo("Entering password");
        WaitUtils.waitForVisibility(driver, password).sendKeys(pwd);
    }

    public void enterConfirmPassword(String pwd) {
        ExtentTestManager.logInfo("Entering confirm password");
        WaitUtils.waitForVisibility(driver, confirmPassword).sendKeys(pwd);
    }

    public void clickRegister() {
        ExtentTestManager.logInfo("Clicking on Register button");
        WaitUtils.clickWhenReady(driver, registerBtn);
    }

    // Validations
    public String getSuccessMessage() {
        ExtentTestManager.logInfo("Reading registration success message");
        return WaitUtils.waitForVisibility(driver, successMsg).getText();
    }

    public String getConfirmPasswordError() {
        ExtentTestManager.logInfo("Reading confirm password error message");
        return WaitUtils.waitForVisibility(driver, confirmPasswordError).getText();
    }

    // ACTION METHOD
    public void registerUser(String fname, String lname, String mail, String pwd) {
        ExtentTestManager.logInfo("Registering new user with email: " + mail);
        selectGenderMale();
        enterFirstName(fname);
        enterLastName(lname);
        enterEmail(mail);
        enterPassword(pwd);
        enterConfirmPassword(pwd);
        clickRegister();
    }

    public void registerUserWithDifferentPasswords(String fname, String lname, String mail, String pwd, String confirmPwd) {
        ExtentTestManager.logInfo("Registering new user with mismatched passwords for email: " + mail);
        selectGenderMale();
        enterFirstName(fname);
        enterLastName(lname);
        enterEmail(mail);
        enterPassword(pwd);
        enterConfirmPassword(confirmPwd);
        clickRegister();
    }
}