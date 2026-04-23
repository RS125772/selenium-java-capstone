package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import com.automation.utils.WaitUtils;
import com.automation.utils.ExtentTestManager;
import java.util.List;

public class ShoppingCartPage {
    private WebDriver driver;

    // ================= LOCATORS =================

    private String removeCheckboxByproduct = "//a[contains(text(),'%s')]/ancestor::tr//input[@name='removefromcart']";
    private String productNameLocator = "//a[contains(@class,'product-name') and contains(text(),'%s')]";
    private String quantityInputByProduct = "//a[contains(text(),'%s')]/ancestor::tr//input[contains(@class,'qty-input')]";
    private By updateCartBtn = By.name("updatecart");
    private By emptyCartMsg = By.cssSelector(".order-summary-content");
    private By addToCartBtn = By.xpath("//input[@value='Add to cart']");

    // ================= CONSTRUCTOR =================
    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= ACTIONS =================

    // Add Product to Cart from Search Results
    public void addProductToCart() {
        ExtentTestManager.logInfo("Adding product to cart");
        WaitUtils.clickWhenReady(driver, addToCartBtn);
        // Wait for add to cart success notification
        WaitUtils.waitForSuccessMessage(driver, By.cssSelector(".bar-notification.success"));
    }

    // Remove Products from Cart
    public boolean isCartEmpty() {
        String text = WaitUtils.waitForVisibility(driver, emptyCartMsg).getText();
        return text.contains("Your Shopping Cart is empty!");
    }

    public void removeProductFromCart(String productName) {
        String removeCheckboxXpath = String.format(removeCheckboxByproduct, productName);
        WaitUtils.waitForClickable(driver, By.xpath(removeCheckboxXpath)).click();
        WaitUtils.waitForClickable(driver, updateCartBtn).click();

    }

    public void removeMultipleProductsFromCart(String products) {
        String[] productArray = products.split(",");
        for (String product : productArray) {
            String productName = product.trim();
            String removeCheckboxXpath = String.format(removeCheckboxByproduct, productName);
            WaitUtils.waitForClickable(driver, By.xpath(removeCheckboxXpath)).click();
        }
        WaitUtils.waitForClickable(driver, updateCartBtn).click();
        WaitUtils.waitForPresence(driver, emptyCartMsg);
    }

    public boolean isProductPresentInCart(String productName) {
        List<WebElement> products = driver.findElements(By.xpath(String.format(productNameLocator, productName)));

        return !products.isEmpty(); // Returns true if product is found, false otherwise
    }

    public boolean areMultipleProductsPresentInCart(String products) {
        String[] productArray = products.split(",");
        for (String product : productArray) {
            String productName = product.trim();
            if (!isProductPresentInCart(productName)) {
                return false;
            }
        }
        return true;
    }

    // Update Quantity of a Product in Cart
    public void updateProductQuantity(String productName, int quantity) {

        WebElement quantityInput = WaitUtils.waitForVisibility(driver,
                By.xpath(String.format(quantityInputByProduct, productName)));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        WaitUtils.waitForClickable(driver, updateCartBtn).click();
    }

    // get price of a product in cart
    public double getProductPrice(String productName) {
        String priceLocator = String
                .format("//a[contains(text(),'%s')]/ancestor::tr//span[@class='product-unit-price']", productName);
        String priceText = WaitUtils.waitForVisibility(driver, By.xpath(priceLocator)).getText();
        return Double.parseDouble(priceText); // Convert price text to double for calculations
    }

    // get quantity of a product in cart
    public int getProductQuantity(String productName) {
        WebElement quantityInput = WaitUtils.waitForVisibility(driver,
                By.xpath(String.format(quantityInputByProduct, productName)));
        return Integer.parseInt(quantityInput.getAttribute("value")); // Get current quantity from input field
    }

    // get total price of a product in cart
    public double getProductTotalPrice(String productName) {
        String totalLocator = String.format("//a[contains(text(),'%s')]/ancestor::tr//span[@class='product-subtotal']",
                productName);
        String totalText = WaitUtils.waitForVisibility(driver, By.xpath(totalLocator)).getText();
        return Double.parseDouble(totalText); // Convert total price text to double for calculations
    }
}