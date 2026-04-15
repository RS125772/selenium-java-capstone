package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.automation.utils.WaitUtils;

public class HomePage {

    private WebDriver driver;

    // ================= LOCATORS =================

    private By registerBtn = By.xpath("//a[contains(text(),'Register')]");

    // ================= CONSTRUCTOR =================

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= ACTIONS =================

    public void clickOnRegisterButton() {
        WaitUtils.waitForClickable(driver, registerBtn).click();
    }


}
