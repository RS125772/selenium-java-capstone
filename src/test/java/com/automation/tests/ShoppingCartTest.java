package com.automation.tests;

import com.automation.pages.ShoppingCartPage;
import com.automation.pages.HomePage;
import com.automation.utils.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.automation.base.BaseTest;
import com.automation.utils.CommonUtils;

public class ShoppingCartTest extends BaseTest {
    // Test methods will go here

    @Test(groups = "requiresLogin", description = "Verify user can remove single product from Cart")
    public void verifyRemoveSingleProductFromCart() {

        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        String productName = testData.getProperty("productName");

        // Step 1: Search for product and add to cart
        homePage.searchProduct(productName);
        cartPage.addProductToCart();
        ExtentTestManager.logInfo("Product added to cart: " + productName);

        // Step 2: Navigate to Cart page
        homePage.clickOnShoppingCart();

        // Step 3: Verify product is in cart
        Assert.assertTrue(cartPage.isProductPresentInCart(productName), "Product not found in cart: " + productName);
        ExtentTestManager.logInfo("Product verified in cart: " + productName);

        // Step 4: Remove product and verify
        cartPage.removeProductFromCart(productName);
        Assert.assertFalse(cartPage.isProductPresentInCart(productName),
                "Product was not removed from the cart: " + productName);
        ExtentTestManager.logInfo("Product successfully removed from cart: " + productName);
        
        commonUtils.takeScreenshot("RemoveSingleProductFromCartTest");
    }

    @Test(groups = "requiresLogin", description = "Verify user can remove multiple products from Cart")
    public void verifyRemoveMultipleProductsFromCart() {

        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        String products = testData.getProperty("products");
        String[] productArray = products.split(",");

        // Step 1: Search and add each product to cart
        for (String product : productArray) {
            String productName = product.trim();
            homePage.searchProduct(productName);
            cartPage.addProductToCart();
            ExtentTestManager.logInfo("Product added to cart: " + productName);
        }

        // Step 2: Navigate to Cart page
        homePage.clickOnShoppingCart();

        // Step 3: Verify all products are in cart
        Assert.assertTrue(cartPage.areMultipleProductsPresentInCart(products),
                "One or more products not found in cart: " + products);
        ExtentTestManager.logInfo("All products verified in cart: " + products);

        // Step 4: Remove products and verify
        cartPage.removeMultipleProductsFromCart(products);
        Assert.assertFalse(cartPage.areMultipleProductsPresentInCart(products),
                "One or more products were not removed from the cart: " + products);
        ExtentTestManager.logInfo("All products successfully removed from cart: " + products);
        
        commonUtils.takeScreenshot("RemoveMultipleProductsFromCartTest");
    }

    @Test(description = "Verify cart is empty")
    public void verifyEmptyCartMessage() {

        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clickOnShoppingCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        boolean isCartEmpty = cartPage.isCartEmpty();
        Assert.assertTrue(isCartEmpty, "Cart is not empty when it should be");
        commonUtils.takeScreenshot("VerifyEmptyCartMessage");
    }

    @Test(groups = "requiresLogin", description = "Verify user can update product quantity in Cart")
    public void verifyUpdateProductQuantityInCart() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        String productName = testData.getProperty("productName");
        int newQuantity = Integer.parseInt(testData.getProperty("quantity"));

        // Step 1: Search for product and add to cart
        homePage.searchProduct(productName);
        cartPage.addProductToCart();
        ExtentTestManager.logInfo("Product added to cart: " + productName);

        // Step 2: Navigate to Cart page
        homePage.clickOnShoppingCart();

        // Step 3: Verify product is in cart
        Assert.assertTrue(cartPage.isProductPresentInCart(productName), "Product not found in cart: " + productName);
        ExtentTestManager.logInfo("Product verified in cart: " + productName);

        // Step 4: Update quantity and verify
        cartPage.updateProductQuantity(productName, newQuantity);
        int updatedQuantity = cartPage.getProductQuantity(productName);
        Assert.assertEquals(updatedQuantity, newQuantity, "Product quantity was not updated correctly");
        ExtentTestManager.logInfo("Product quantity successfully updated to: " + newQuantity);
        
        commonUtils.takeScreenshot("UpdateProductQuantityInCartTest");
    }

    @Test(groups = "requiresLogin", description = "Verify user can see correct price for product in Cart")
    public void verifyProductPriceInCart() {
        CommonUtils commonUtils = new CommonUtils(driver);
        HomePage homePage = new HomePage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        String productName = testData.getProperty("productName");

        // Step 1: Search for product and add to cart
        homePage.searchProduct(productName);
        cartPage.addProductToCart();
        ExtentTestManager.logInfo("Product added to cart: " + productName);

        // Step 2: Navigate to Cart page
        homePage.clickOnShoppingCart();

        // Step 3: Verify product is in cart
        Assert.assertTrue(cartPage.isProductPresentInCart(productName), "Product not found in cart: " + productName);
        ExtentTestManager.logInfo("Product verified in cart: " + productName);

        // Step 4: Get and verify price details
        double price = cartPage.getProductPrice(productName);
        int quantity = cartPage.getProductQuantity(productName);
        double totalPrice = cartPage.getProductTotalPrice(productName);
        double expectedTotalPrice = price * quantity;

        // Assertion: Verify total price matches expected calculation
        Assert.assertEquals(totalPrice, expectedTotalPrice, 0.01, "Total price does not match expected value");

        // Assertion: Verify price is greater than zero
        Assert.assertTrue(price > 0, "Product price should be greater than zero");
        ExtentTestManager.logInfo("Price verification successful - Price: " + price + ", Quantity: " + quantity + ", Total: " + totalPrice);
        
        System.out.print(totalPrice);
        commonUtils.takeScreenshot("VerifyProductPriceInCartTest");
    }

}