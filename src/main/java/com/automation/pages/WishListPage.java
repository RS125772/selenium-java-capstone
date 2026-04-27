package com.automation.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.utils.ExtentTestManager;
import com.automation.utils.WaitUtils;
import java.util.List;

import org.openqa.selenium.By;

public class WishListPage {
    private WebDriver driver;
    // ================= LOCATORS =================
    private String removeWishlistCheckboxByProduct = "//a[contains(text(),'%s')]/ancestor::tr//input[contains(@type,'checkbox') and contains(@name,'remove')]";
    private By updateWishlistBtn = By.xpath("//input[@name='updatecart' or @name='updatewishlist' or @value='Update wishlist']");       
  
    // ================= CONSTRUCTOR =================
    public WishListPage(WebDriver driver) {
        this.driver = driver;
    }   

     public void removeProductsFromWishlist(String products) {
        ExtentTestManager.logInfo("Removing products from wishlist: " + products);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnWishlist();

        String[] productList = products.split(",");
        for (String product : productList) {
            String productName = product.trim();
            By removeCheckbox = By.xpath(String.format(removeWishlistCheckboxByProduct, productName));
            WaitUtils.waitForClickable(driver, removeCheckbox).click();
        }

        List<WebElement> updateButtons = driver.findElements(updateWishlistBtn);
        if (!updateButtons.isEmpty()) {
            WaitUtils.waitForClickable(driver, updateWishlistBtn).click();
        }

        WaitUtils.waitForPageToLoad(driver);
    }

    public boolean isProductsPresentInWishlist(String products) {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnWishlist();
        String[] productList = products.split(",");
        for (String product : productList) {
            String productName = product.trim();
            String productLinkXpath = String.format("//td[@class='product']/a[contains(text(),'%s')]", productName);
            List<WebElement> foundProducts = driver.findElements(By.xpath(productLinkXpath));
            if (foundProducts.isEmpty()) {
                return false; // If any product is not found, return false
            }
        }
        return true; // All products are present
    }

}