package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.automation.utils.WaitUtils;

public class HomePage {

    private WebDriver driver;

    // ================= LOCATORS =================

    // Header links
    private By registerBtn = By.xpath("//a[contains(text(),'Register')]");
    private By loginBtn = By.xpath("//a[contains(text(),'Log in')]");
    private By shoppingCart = By.xpath("//span[contains(text(),'Shopping cart')]");
    private By wishlist = By.xpath("//span[contains(text(),'Wishlist')]");

    // Search Locators
    private By searchBox = By.id("small-searchterms");
    private By searchBtn = By.xpath("//input[@value='Search']");

    // Navigation Menu categories
    private By booksMenu = By.xpath("//a[contains(text(),'Books')]");
    private By computersMenu = By.xpath("//a[contains(text(),'Computers')]");
    private By electronicsMenu = By.xpath("//a[contains(text(),'Electronics')]");
    private By apparelMenu = By.xpath("//a[contains(text(),'Apparel & Shoes')]");
    private By digitalDownloadsMenu = By.xpath("//a[contains(text(),'Digital downloads')]");
    private By jewelryMenu = By.xpath("//a[contains(text(),'Jewelry')]");
    private By giftCardsMenu = By.xpath("//a[contains(text(),'Gift Cards')]");

    //Page Validation locators
    private By booksPageTitle = By.xpath("//h1[contains(text(),'Books')]");
    private By registerTitle = By.xpath("//h1[contains(text(),'Register')]");
    private By computersPageTitle = By.xpath("//h1[contains(text(),'Computers')]");
    private By electronicsPageTitle = By.xpath("//h1[contains(text(),'Electronics')]");
    private By apparelPageTitle = By.xpath("//h1[contains(text(),'Apparel')]");
    private By searchResultsTitle = By.xpath("//h1[contains(text(),'Search')]");
    private By digitalDownloadsTitle = By.xpath("//h1[contains(text(),'Digital downloads')]");
    private By jewelryTitle = By.xpath("//h1[contains(text(),'Jewelry')]");
    private By giftCardsTitle = By.xpath("//h1[contains(text(),'Gift Cards')]");

    // ================= CONSTRUCTOR =================
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= ACTIONS =================

    public void clickOnRegisterButton() {
        WaitUtils.waitForClickable(driver, registerBtn).click();
    }

    public void clickOnLogin() {
        WaitUtils.waitForClickable(driver, loginBtn).click();
    }

    public void clickOnShoppingCart() {
        WaitUtils.waitForClickable(driver, shoppingCart).click();
    }

    public void clickOnWishlist() {
        WaitUtils.waitForClickable(driver, wishlist).click();
    }

    // ================= SEARCH FUNCTIONALITY =================

    public void enterSearchText(String product) {
        WaitUtils.waitForVisibility(driver, searchBox).clear();
        driver.findElement(searchBox).sendKeys(product);
    }

    public void clickSearch() {
        WaitUtils.waitForClickable(driver, searchBtn).click();
    }

    // 🔥 Business Method (Best Practice)
    public void searchProduct(String product) {
        enterSearchText(product);
        clickSearch();
    }

    // ================= NAVIGATION ACTIONS =================

    public void clickBooks() {
        WaitUtils.waitForClickable(driver, booksMenu).click();
    }

    public void clickComputers() {
        WaitUtils.waitForClickable(driver, computersMenu).click();
    }

    public void clickElectronics() {
        WaitUtils.waitForClickable(driver, electronicsMenu).click();
    }

    public void clickApparel() {
        WaitUtils.waitForClickable(driver, apparelMenu).click();
    }

    public void clickDigitalDownloads() {
        WaitUtils.waitForClickable(driver, digitalDownloadsMenu).click();
    }

    public void clickJewelry() {
        WaitUtils.waitForClickable(driver, jewelryMenu).click();
    }

    public void clickGiftCards() {
        WaitUtils.waitForClickable(driver, giftCardsMenu).click();
    }

    // ================= VALIDATIONS =================

    public boolean isBooksPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, booksPageTitle).isDisplayed();
    }

    public boolean isRegisterPageDisplayed() {
        return driver.findElement(registerTitle).isDisplayed();
    }

    public boolean isComputersPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, computersPageTitle).isDisplayed();
    }

    public boolean isElectronicsPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, electronicsPageTitle).isDisplayed();
    }

    public boolean isApparelPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, apparelPageTitle).isDisplayed();
    }

    public boolean isSearchResultsDisplayed() {
        return WaitUtils.waitForVisibility(driver, searchResultsTitle).isDisplayed();
    }

    public boolean isDigitalDownloadsPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, digitalDownloadsTitle).isDisplayed();
    }

    public boolean isJewelryPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, jewelryTitle).isDisplayed();
    }

    public boolean isGiftCardsPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, giftCardsTitle).isDisplayed();
    }
}