package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void verifySearchFunctionality()
    {

        HomePage homePage = new HomePage(driver);

        //Search Functionality
        homePage.searchProduct("computer");
        Assert.assertTrue(homePage.isSearchResultsDisplayed(),"Search results page is not displayed");

        //Navigate to Books
        driver.navigate().back();
        homePage.clickBooks();
        Assert.assertTrue(homePage.isBooksPageDisplayed(),"Books page is not displayed");

        //Navigate to Computers
        homePage.clickComputers();
        Assert.assertTrue(homePage.isComputersPageDisplayed(),"Computers page is not displayed");

        //Navigate to Electronics
        homePage.clickElectronics();
        Assert.assertTrue(homePage.isElectronicsPageDisplayed(),"Electronics page is not displayed");

        //Navigate to Apparel
        homePage.clickApparel();
        Assert.assertTrue(homePage.isApparelPageDisplayed(),"Apparel page is not displayed");

        //Navigate to Digital Downloads
        homePage.clickDigitalDownloads();
        Assert.assertTrue(homePage.isDigitalDownloadsPageDisplayed(),"Digital Downloads page is not displayed");

        //Navigate to Jewelry
        homePage.clickJewelry();
        Assert.assertTrue(homePage.isJewelryPageDisplayed(),"Jewelry page is not displayed");

        //Navigate to Gift Cards
        homePage.clickGiftCards();
        Assert.assertTrue(homePage.isGiftCardsPageDisplayed(),"Gift Cards page is not displayed");

        //Navigate to Register Page
        homePage.clickOnRegisterButton();
        Assert.assertTrue(homePage.isRegisterPageDisplayed(),"Register page is not displayed");
    }
}