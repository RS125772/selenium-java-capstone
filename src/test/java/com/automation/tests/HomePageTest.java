package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.utils.CommonUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(description = "Verify page navigation on each category")
    public void verifyCategoryNavigationFunctionality() {

        HomePage homePage = new HomePage(driver);
        CommonUtils commonUtils = new CommonUtils(driver);

        // Navigate to Books
        homePage.clickBooks();
        Assert.assertTrue(homePage.isBooksPageDisplayed(), "Books page is not displayed");
        commonUtils.takeScreenshot("BooksPage");

        // Navigate to Computers
        homePage.clickComputers();
        Assert.assertTrue(homePage.isComputersPageDisplayed(), "Computers page is not displayed");
        commonUtils.takeScreenshot("ComputersPage");

        // Navigate to Electronics
        homePage.clickElectronics();
        Assert.assertTrue(homePage.isElectronicsPageDisplayed(), "Electronics page is not displayed");
        commonUtils.takeScreenshot("ElectronicsPage");

        // Navigate to Apparel
        homePage.clickApparel();
        Assert.assertTrue(homePage.isApparelPageDisplayed(), "Apparel page is not displayed");
        commonUtils.takeScreenshot("ApparelPage");

        // Navigate to Digital Downloads
        homePage.clickDigitalDownloads();
        Assert.assertTrue(homePage.isDigitalDownloadsPageDisplayed(), "Digital Downloads page is not displayed");
        commonUtils.takeScreenshot("DigitalDownloadsPage");

        // Navigate to Jewelry
        homePage.clickJewelry();
        Assert.assertTrue(homePage.isJewelryPageDisplayed(), "Jewelry page is not displayed");
        commonUtils.takeScreenshot("JewelryPage");

        // Navigate to Gift Cards
        homePage.clickGiftCards();
        Assert.assertTrue(homePage.isGiftCardsPageDisplayed(), "Gift Cards page is not displayed");
        commonUtils.takeScreenshot("GiftCardsPage");

        // Navigate to Register Page
        homePage.clickOnRegisterButton();
        Assert.assertTrue(homePage.isRegisterPageDisplayed(), "Register page is not displayed");
        commonUtils.takeScreenshot("RegisterPage");
    }

    @Test(description = "Verify Search Functionality")
    public void verifyValidSearchFunctionality() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(testData.getProperty("searchProduct"));
        Assert.assertTrue(homePage.isSearchResultsDisplayed(), "Search results are not displayed");
        commonUtils.takeScreenshot("ValidSearchTest");
    }

    @Test(description = "Verify Invalid Search")
    public void verifyInvalidSearchFunctionality() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(testData.getProperty("invalidSearchProduct"));
        Assert.assertTrue(homePage.isNoSearchResultDisplayed(),"No result message is not displayed for invalid search");
        commonUtils.takeScreenshot("InvalidSearchTest");
    }

    @Test(description = "Verify search with blank input shows alert")
public void verifySearchWithBlankInput() {

    HomePage homePage = new HomePage(driver);
    CommonUtils commonUtils = new CommonUtils(driver);

    // Step 1: Perform blank search
    homePage.searchWithBlankInput();

    // Step 2: Get alert text
    String alertMsg = commonUtils.getAlertText();

    // Step 3: Validate alert
    Assert.assertEquals(alertMsg, "Please enter some search keyword",
            "Alert message mismatch!");

    // Step 4: Accept alert
    commonUtils.acceptAlert();

    // Step 5: Screenshot
    commonUtils.takeScreenshot("BlankSearchAlert");
}

}