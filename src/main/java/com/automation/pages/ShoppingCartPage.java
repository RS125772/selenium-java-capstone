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
private By successMessage = By.cssSelector(".bar-notification.success");
private By productGrid = By.cssSelector(".product-grid");

// ================= CONSTRUCTOR =================

public ShoppingCartPage(WebDriver driver) {
    this.driver = driver;
}

// ================= ADD TO CART =================

public void addProductToCart(String productName) {

    ExtentTestManager.logInfo("Adding product to cart: " + productName);

    // Wait for product list to load
    WaitUtils.waitForVisibility(driver, productGrid);

    String addToCartXpath = String.format(
        "//div[contains(@class,'product-item')]//a[contains(normalize-space(),'%s')]" +
        "/ancestor::div[contains(@class,'product-item')]//input[@value='Add to cart']",
        productName
    );

    By addToCartButton = By.xpath(addToCartXpath);

    WebElement button = WaitUtils.waitForClickable(driver, addToCartButton);
    button.click();

    WaitUtils.waitForVisibility(driver, successMessage);
}

// ================= CART VALIDATIONS METHODS=================

public boolean isProductAddedSuccessMessageDisplayed() {
    String msg = WaitUtils.waitForVisibility(driver, successMessage).getText();
    return msg.toLowerCase().contains("added");
}

public boolean isCartEmpty() {
    String text = WaitUtils.waitForVisibility(driver, emptyCartMsg).getText();
    return text.toLowerCase().contains("empty");
}

public boolean isProductPresentInCart(String productName) {
    try {
        return WaitUtils.waitForVisibility(
            driver,
            By.xpath(String.format(productNameLocator, productName))
        ).isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

public boolean areMultipleProductsPresentInCart(String products) {
    String[] productArray = products.split(",");
    for (String product : productArray) {
        if (!isProductPresentInCart(product.trim())) {
            return false;
        }
    }
    return true;
}

// ================= REMOVE METHODS=================

public void removeProductFromCart(String productName) {
    String xpath = String.format(removeCheckboxByproduct, productName);
    WaitUtils.waitForClickable(driver, By.xpath(xpath)).click();
    WaitUtils.clickWhenReady(driver, updateCartBtn);
}

public void removeMultipleProductsFromCart(String products) {
    String[] productArray = products.split(",");

    for (String product : productArray) {
        String xpath = String.format(removeCheckboxByproduct, product.trim());
        WaitUtils.waitForClickable(driver, By.xpath(xpath)).click();
    }

    WaitUtils.clickWhenReady(driver, updateCartBtn);
    WaitUtils.waitForVisibility(driver, emptyCartMsg);
}

public void clearCartIfNotEmpty() {

    List<WebElement> checkboxes = driver.findElements(By.name("removefromcart"));

    if (!checkboxes.isEmpty()) {

        ExtentTestManager.logInfo("Clearing cart before test");

        for (WebElement checkbox : checkboxes) {
            checkbox.click();
        }

        WaitUtils.clickWhenReady(driver, updateCartBtn);
        WaitUtils.waitForVisibility(driver, emptyCartMsg);
    }
}

// ================= QUANTITY METHODS=================

public void updateProductQuantity(String productName, int quantity) {

    WebElement input = WaitUtils.waitForVisibility(driver,
            By.xpath(String.format(quantityInputByProduct, productName)));

    input.clear();
    input.sendKeys(String.valueOf(quantity));

    WaitUtils.clickWhenReady(driver, updateCartBtn);
}

public int getProductQuantity(String productName) {

    WebElement input = WaitUtils.waitForVisibility(driver,
            By.xpath(String.format(quantityInputByProduct, productName)));

    return Integer.parseInt(input.getAttribute("value"));
}

// ================= PRICE =================

public double getProductPrice(String productName) {

    String xpath = String.format(
        "//a[contains(text(),'%s')]/ancestor::tr//span[@class='product-unit-price']",
        productName
    );

    String price = WaitUtils.waitForVisibility(driver, By.xpath(xpath)).getText();

    return Double.parseDouble(price.replaceAll("[^0-9.]", ""));
}

public double getProductTotalPrice(String productName) {

    String xpath = String.format(
        "//a[contains(text(),'%s')]/ancestor::tr//span[@class='product-subtotal']",
        productName
    );

    String total = WaitUtils.waitForVisibility(driver, By.xpath(xpath)).getText();

    return Double.parseDouble(total.replaceAll("[^0-9.]", ""));
}
}
