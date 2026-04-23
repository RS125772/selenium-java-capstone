package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.ShoppingCartPage;
import com.automation.utils.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {

@Test(description = "Verify user can remove single product from Cart")
public void verifyRemoveSingleProductFromCart() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    String productName = testData.getProperty("productName");

    homePage.searchProduct(productName);
    homePage.waitForSearchResults(); // 🔥 REQUIRED

    cartPage.addProductToCart(productName);

    homePage.clickOnShoppingCart();

    Assert.assertTrue(cartPage.isProductPresentInCart(productName),
            "Product not found in cart");

    cartPage.removeProductFromCart(productName);

    Assert.assertFalse(cartPage.isProductPresentInCart(productName),
            "Product not removed");

    utils.takeScreenshot("RemoveSingleProduct");
}

@Test(description = "Verify user can remove multiple products from Cart")
public void verifyRemoveMultipleProductsFromCart() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    String products = testData.getProperty("products");
    String[] productArray = products.split(",");

    for (String product : productArray) {

        String productName = product.trim();

        homePage.searchProduct(productName);
        homePage.waitForSearchResults();

        cartPage.addProductToCart(productName);
    }

    homePage.clickOnShoppingCart();

    Assert.assertTrue(cartPage.areMultipleProductsPresentInCart(products),
            "Products missing in cart");

    cartPage.removeMultipleProductsFromCart(products);

    Assert.assertTrue(cartPage.isCartEmpty(),
            "Cart not empty after removal");

    utils.takeScreenshot("RemoveMultipleProducts");
}

@Test(description = "Verify cart is empty")
public void verifyEmptyCartMessage() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    homePage.clickOnShoppingCart();

    cartPage.clearCartIfNotEmpty();

    Assert.assertTrue(cartPage.isCartEmpty(),
            "Cart should be empty");

    utils.takeScreenshot("EmptyCart");
}

@Test(description = "Verify update product quantity")
public void verifyUpdateProductQuantityInCart() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    String productName = testData.getProperty("productName");
    int quantity = Integer.parseInt(testData.getProperty("quantity"));

    homePage.searchProduct(productName);
    homePage.waitForSearchResults();

    cartPage.addProductToCart(productName);

    homePage.clickOnShoppingCart();

    cartPage.updateProductQuantity(productName, quantity);

    int updatedQty = cartPage.getProductQuantity(productName);

    Assert.assertEquals(updatedQty, quantity,
            "Quantity not updated");

    utils.takeScreenshot("UpdateQuantity");
}

@Test(description = "Verify product price calculation")
public void verifyProductPriceInCart() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    String productName = testData.getProperty("productName");

    homePage.searchProduct(productName);
    homePage.waitForSearchResults();

    cartPage.addProductToCart(productName);

    homePage.clickOnShoppingCart();

    double price = cartPage.getProductPrice(productName);
    int qty = cartPage.getProductQuantity(productName);
    double total = cartPage.getProductTotalPrice(productName);

    Assert.assertEquals(total, price * qty, 0.01,
            "Total price mismatch");

    Assert.assertTrue(price > 0,
            "Invalid product price");

    utils.takeScreenshot("PriceValidation");
}

@Test(description = "Verify product added successfully")
public void verifyProductAddedSuccessfully() {

    CommonUtils utils = new CommonUtils(driver);
    HomePage homePage = new HomePage(driver);
    ShoppingCartPage cartPage = new ShoppingCartPage(driver);

    String productName = testData.getProperty("productName");

    homePage.searchProduct(productName);
    homePage.waitForSearchResults();

    cartPage.addProductToCart(productName);

    Assert.assertTrue(
            cartPage.isProductAddedSuccessMessageDisplayed(),
            "Product not added"
    );

    homePage.clickOnShoppingCart();

    Assert.assertTrue(
            cartPage.isProductPresentInCart(productName),
            "Product missing in cart"
    );

    utils.takeScreenshot("ProductAdded");
}

}
