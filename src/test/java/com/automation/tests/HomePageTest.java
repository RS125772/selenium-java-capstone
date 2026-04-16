package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void verifySearchFunctionality() {

        HomePage homePage = new HomePage(driver);

        homePage.searchProduct("computer");

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("search"), "Search functionality is not working");
    }

    @Test
    public void verifyNavigationToBooksPage() {

        HomePage homePage = new HomePage(driver);

        homePage.clickBooks();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("books"), "Navigation to Books page failed");
    }

    @Test
    public void verifyRegisterButtonNavigation() {

        HomePage homePage = new HomePage(driver);

        homePage.clickOnRegisterButton();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("register"), "Register page navigation failed");
    }
}