package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    private WebDriver driver;

    public RegisterPage(WebDriver driver) {
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

    // Actions
    public void selectGenderMale() {
        driver.findElement(genderMale).click();
    }

    public void enterFirstName(String fname) {
        driver.findElement(firstName).sendKeys(fname);
    }

    public void enterLastName(String lname) {
        driver.findElement(lastName).sendKeys(lname);
    }

    public void enterEmail(String mail) {
        driver.findElement(email).sendKeys(mail);
    }

    public void enterPassword(String pwd) {
        driver.findElement(password).sendKeys(pwd);
    }

    public void enterConfirmPassword(String pwd) {
        driver.findElement(confirmPassword).sendKeys(pwd);
    }

    public void clickRegister() {
        driver.findElement(registerBtn).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMsg).getText();
    }

    // Business Method (Best Practice 🔥)
    public void registerUser(String fname, String lname, String mail, String pwd) {
        selectGenderMale();
        enterFirstName(fname);
        enterLastName(lname);
        enterEmail(mail);
        enterPassword(pwd);
        enterConfirmPassword(pwd);
        clickRegister();
    }
}