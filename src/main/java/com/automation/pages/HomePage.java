package com.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.utils.ExtentTestManager;
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
    private By noSearchResultMsg = By.xpath("//strong[contains(text(),'No products were found that matched your criteria.')]");

    // Newsletter subscription locators
    private By newsletterEmailInput = By.id("newsletter-email");
    private By newsletterSubscribeBtn = By.id("newsletter-subscribe-button");
    private By newsletterSuccessMsg = By.id("newsletter-result-block");

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

    private By addToWishlistBtn = By.xpath("//input[@value='Add to wishlist']");

    private By footerLinks = By.cssSelector(".footer a");
    
    // ================= CONSTRUCTOR =================
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= ACTIONS =================

    public void clickOnRegisterButton() {
        ExtentTestManager.logInfo("Clicking on Register button");
        WaitUtils.clickWhenReady(driver, registerBtn);
    }

    public void clickOnLogin() {
        ExtentTestManager.logInfo("Clicking on Login button");
        WaitUtils.clickWhenReady(driver, loginBtn);
    }

    public void clickOnShoppingCart() {
        ExtentTestManager.logInfo("Clicking on Shopping Cart");
        WaitUtils.clickWhenReady(driver, shoppingCart);
    }

    public void clickOnWishlist() {
        ExtentTestManager.logInfo("Clicking on Wishlist");
        WaitUtils.clickWhenReady(driver, wishlist);
    }

    public void addProductsToWishlist(String products) {
        ExtentTestManager.logInfo("Adding products to wishlist: " + products);
        String[] productList = products.split(",");
        for (String product : productList) {
            String productName = product.trim();
            searchProduct(productName);

            By productLinkForName = By.xpath(String.format("//h2[@class='product-title']/a[contains(text(),'%s')]", productName));
            //WaitUtils.waitForVisibility(driver, productLinkForName).isDisplayed();
            WaitUtils.waitForClickable(driver, productLinkForName).click();
            //WaitUtils.waitForVisibility(driver, addToWishlistBtn);
            WaitUtils.waitForClickable(driver, addToWishlistBtn).click();
            WaitUtils.waitForPageToLoad(driver);
        }
    }

    public List<String> getAllFooterOptions() {
    ExtentTestManager.logInfo("Fetching all footer options");
    List<WebElement> footerElements = driver.findElements(footerLinks);
    List<String> actualFooterOptions = new ArrayList<>();
    for (WebElement element : footerElements) {
        actualFooterOptions.add(element.getText().trim());
    }
    return actualFooterOptions;
}
    // ================= SEARCH FUNCTIONALITY =================

    public void enterSearchText(String product) {
        ExtentTestManager.logInfo("Entering search text: " + product);
        WaitUtils.waitForVisibility(driver, searchBox).clear();
        driver.findElement(searchBox).sendKeys(product);
    }

    public void clickSearch() {
        ExtentTestManager.logInfo("Clicking on Search button");
        WaitUtils.waitForClickable(driver, searchBtn).click();
    }

    //Business Method
    public void searchProduct(String product) {
        ExtentTestManager.logInfo("Searching for product: " + product);
        enterSearchText(product);
        clickSearch();
    }

    public void searchWithBlankInput() {
    ExtentTestManager.logInfo("Performing blank search");
    WaitUtils.waitForVisibility(driver, searchBox).clear();

    ExtentTestManager.logInfo("Clicking on Search button");
    WaitUtils.waitForClickable(driver, searchBtn).click();
}

    // ================= NEWSLETTER ACTIONS =================

    public void enterNewsletterEmail(String email) {
        ExtentTestManager.logInfo("Entering newsletter email: " + email);
        WaitUtils.waitForVisibility(driver, newsletterEmailInput).clear();
        driver.findElement(newsletterEmailInput).sendKeys(email);
    }

    public void clickSubscribeNewsletter() {
        ExtentTestManager.logInfo("Clicking Newsletter Subscribe button");
        WaitUtils.clickWhenReady(driver, newsletterSubscribeBtn);
    }

    public void subscribeToNewsletter(String email) {
    ExtentTestManager.logInfo("Subscribing to newsletter with email: " + email);
    enterNewsletterEmail(email);
    clickSubscribeNewsletter();
    WaitUtils.waitForTextToBePresent(driver, newsletterSuccessMsg, "Thank");
}

    public String getNewsletterSubscriptionSuccessMessage() {
        ExtentTestManager.logInfo("Getting newsletter subscription success message");
        return WaitUtils.waitForVisibility(driver, newsletterSuccessMsg).getText().trim();
    }

    public boolean isNewsletterSubscriptionSuccessDisplayed() {
        ExtentTestManager.logInfo("Verifying newsletter subscription success message");
        return WaitUtils.waitForVisibility(driver, newsletterSuccessMsg).isDisplayed();
    }

    // ================= NAVIGATION ACTIONS =================

    public void clickBooks() {
        ExtentTestManager.logInfo("Clicking on Books category");
        WaitUtils.clickWhenReady(driver, booksMenu);
    }

    public void clickComputers() {
        ExtentTestManager.logInfo("Clicking on Computers category");
        WaitUtils.clickWhenReady(driver, computersMenu);
    }

    public void clickElectronics() {
        ExtentTestManager.logInfo("Clicking on Electronics category");
        WaitUtils.clickWhenReady(driver, electronicsMenu);
    }

    public void clickApparel() {
        ExtentTestManager.logInfo("Clicking on Apparel & Shoes category");
        WaitUtils.clickWhenReady(driver, apparelMenu);
    }

    public void clickDigitalDownloads() {
        ExtentTestManager.logInfo("Clicking on Digital Downloads category");
        WaitUtils.clickWhenReady(driver, digitalDownloadsMenu);
    }

    public void clickJewelry() {
        ExtentTestManager.logInfo("Clicking on Jewelry category");
        WaitUtils.clickWhenReady(driver, jewelryMenu);
    }

    public void clickGiftCards() {
        ExtentTestManager.logInfo("Clicking on Gift Cards category");
        WaitUtils.clickWhenReady(driver, giftCardsMenu);
    }

    // ================= VALIDATION METHODS=================

    public boolean isBooksPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Books page is displayed");
        return WaitUtils.waitForVisibility(driver, booksPageTitle).isDisplayed();
    }

    public boolean isRegisterPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Register page is displayed");
        return WaitUtils.waitForVisibility(driver, registerTitle).isDisplayed();
    }

    public boolean isComputersPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Computers page is displayed");
        return WaitUtils.waitForVisibility(driver, computersPageTitle).isDisplayed();
    }

    public boolean isElectronicsPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Electronics page is displayed");
        return WaitUtils.waitForVisibility(driver, electronicsPageTitle).isDisplayed();
    }

    public boolean isApparelPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Apparel page is displayed");
        return WaitUtils.waitForVisibility(driver, apparelPageTitle).isDisplayed();
    }

    public boolean isSearchResultsDisplayed() {
        ExtentTestManager.logInfo("Verifying search results page is displayed");
        return WaitUtils.waitForVisibility(driver, searchResultsTitle).isDisplayed();
    }

    public boolean isDigitalDownloadsPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Digital Downloads page is displayed");
        return WaitUtils.waitForVisibility(driver, digitalDownloadsTitle).isDisplayed();
    }

    public boolean isJewelryPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Jewelry page is displayed");
        return WaitUtils.waitForVisibility(driver, jewelryTitle).isDisplayed();
    }

    public boolean isGiftCardsPageDisplayed() {
        ExtentTestManager.logInfo("Verifying Gift Cards page is displayed");
        return WaitUtils.waitForVisibility(driver, giftCardsTitle).isDisplayed();
    }

    public boolean isNoSearchResultDisplayed() {
        ExtentTestManager.logInfo("Verifying no search results message is displayed");
        return WaitUtils.waitForVisibility(driver, noSearchResultMsg).isDisplayed();
    }

    public void waitForSearchResults() {
    By productGrid = By.cssSelector(".product-grid");
    WaitUtils.waitForVisibility(driver, productGrid);
}
}
