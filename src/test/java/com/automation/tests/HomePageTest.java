package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.utils.CommonUtils;
import com.automation.utils.WaitUtils;

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

}