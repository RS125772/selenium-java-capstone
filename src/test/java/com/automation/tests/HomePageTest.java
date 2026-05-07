package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.utils.CommonUtils;
import com.automation.utils.WaitUtils;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.utils.RetryAnalyzer;

public class HomePageTest extends BaseTest {

    @Test(description = "Verify page navigation on each category", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyCategoryNavigationFunctionality() {
        HomePage homePage = new HomePage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);
        homePage.clickBooks();
        Assert.assertTrue(homePage.isBooksPageDisplayed(), "Books page is not displayed");
        commonUtils.takeScreenshot("BooksPage");
        homePage.clickComputers();
        Assert.assertTrue(homePage.isComputersPageDisplayed(), "Computers page is not displayed");
        commonUtils.takeScreenshot("ComputersPage");
        homePage.clickElectronics();
        Assert.assertTrue(homePage.isElectronicsPageDisplayed(), "Electronics page is not displayed");
        commonUtils.takeScreenshot("ElectronicsPage");
        homePage.clickApparel();
        Assert.assertTrue(homePage.isApparelPageDisplayed(), "Apparel page is not displayed");
        commonUtils.takeScreenshot("ApparelPage");
        homePage.clickDigitalDownloads();
        Assert.assertTrue(homePage.isDigitalDownloadsPageDisplayed(), "Digital Downloads page is not displayed");
        commonUtils.takeScreenshot("DigitalDownloadsPage");
        homePage.clickJewelry();
        Assert.assertTrue(homePage.isJewelryPageDisplayed(), "Jewelry page is not displayed");
        commonUtils.takeScreenshot("JewelryPage");
        homePage.clickGiftCards();
        Assert.assertTrue(homePage.isGiftCardsPageDisplayed(), "Gift Cards page is not displayed");
        commonUtils.takeScreenshot("GiftCardsPage");
        homePage.clickOnRegisterButton();
        Assert.assertTrue(homePage.isRegisterPageDisplayed(), "Register page is not displayed");
        commonUtils.takeScreenshot("RegisterPage");
    }

    @Test(description = "Verify Search Functionality", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyValidSearchFunctionality() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(testData.getProperty("searchProduct"));
        Assert.assertTrue(homePage.isSearchResultsDisplayed(), "Search results are not displayed");
        commonUtils.takeScreenshot("ValidSearchTest");
    }

    @Test(description = "Verify Invalid Search", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyInvalidSearchFunctionality() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(testData.getProperty("invalidSearchProduct"));
        Assert.assertTrue(homePage.isNoSearchResultDisplayed(),
                "No result message is not displayed for invalid search");
        commonUtils.takeScreenshot("InvalidSearchTest");
    }

    @Test(description = "Verify Newsletter subscription functionality", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifyNewsletterSubscriptionFunctionality() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        String newsletterEmail = "automation" + System.currentTimeMillis() + "@example.com";
        homePage.subscribeToNewsletter(newsletterEmail);
        Assert.assertTrue(homePage.isNewsletterSubscriptionSuccessDisplayed(),
                "Newsletter subscription success message was not displayed");
        String actualMessage = homePage.getNewsletterSubscriptionSuccessMessage();
Assert.assertTrue(actualMessage.contains("Thank you for signing up"),"Unexpected newsletter message: " + actualMessage
);
        commonUtils.takeScreenshot("NewsletterSubscriptionTest");
    }

    @Test(description = "Verify search with blank input shows alert", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
    public void verifySearchWithBlankInput() {

        HomePage homePage = new HomePage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);
        homePage.searchWithBlankInput();
        String alertMsg = WaitUtils.waitForAlert(driver).getText();
        Assert.assertEquals(alertMsg, "Please enter some search keyword", "Alert message mismatch!");
        WaitUtils.waitForAlert(driver).accept();
        commonUtils.takeScreenshot("BlankSearch_Alert");
    }

    @Test(description = "Verify all footer options are displayed correctly", groups = { "smoke" }, retryAnalyzer = RetryAnalyzer.class)
public void verifyFooterOptionsDisplayedCorrectly() {
    CommonUtils commonUtils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    List<String> expectedFooterOptions = Arrays.asList(

            "Sitemap",
            "Shipping & Returns",
            "Privacy Notice",
            "Conditions of Use",
            "About us",
            "Contact us",

            "Search",
            "News",
            "Blog",
            "Recently viewed products",
            "Compare products list",
            "New products",

            "My account",
            "Orders",
            "Addresses",
            "Shopping cart",
            "Wishlist",

            "Facebook",
            "Twitter",
            "RSS",
            "YouTube",
            "Google+"
    );

    List<String> actualFooterOptions = homePage.getAllFooterOptions();
    Assert.assertTrue(actualFooterOptions.containsAll(expectedFooterOptions),"Some footer options are missing");
    commonUtils.takeScreenshot("FooterValidation_" + System.currentTimeMillis());
    }
}