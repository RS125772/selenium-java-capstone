package com.automation.tests;
import com.automation.pages.HomePage;
import com.automation.pages.WishListPage;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.automation.base.BaseTest;
import com.automation.utils.CommonUtils;

public class WishListTest extends BaseTest {

    @Test(groups = "requiresLogin", description = "Verify user can add product to wishlist")
    public void verifyAddProductToWishlist() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        WishListPage wishListPage = new WishListPage(driver);

        String productName = testData.getProperty("products");
        homePage.addProductsToWishlist(productName);

        homePage.clickOnWishlist();

        Assert.assertTrue(wishListPage.isProductsPresentInWishlist(productName), "Product not added to wishlist: " + productName);
        commonUtils.takeScreenshot("AddProductToWishlistTest");
    }

    @Test(groups = "requiresLogin", description = "Verify user can remove product from wishlist")
    public void verifyRemoveProductFromWishlist() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        WishListPage wishListPage = new WishListPage(driver);

        String productName = testData.getProperty("products");
        homePage.addProductsToWishlist(productName);
        homePage.clickOnWishlist();
        wishListPage.removeProductsFromWishlist(productName);

        Assert.assertFalse(wishListPage.isProductsPresentInWishlist(productName), "Product was not removed from wishlist: " + productName);
        commonUtils.takeScreenshot("RemoveProductFromWishlistTest");
    }
}