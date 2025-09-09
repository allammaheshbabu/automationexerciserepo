package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCartPageDisplayed() {
        try {
            WebElement cartTitle = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//section[@id='cart_items']//h2[contains(text(),'Shopping Cart')]")
                    ));
            return cartTitle.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


    // Get product price as double
    public double getProductPrice(String productName) {
        try {
            WebElement priceElement = driver.findElement(
                    By.xpath("//td[contains(text(),'" + productName + "')]/following-sibling::td[@class='cart_price']")
            );
            String priceText = priceElement.getText(); // e.g., "Rs. 500"
            priceText = priceText.replaceAll("[^0-9.]", ""); // remove "Rs." and spaces
            return Double.parseDouble(priceText);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Price not found for product: " + productName);
        }
    }

    // Get product quantity as integer
    public int getProductQuantity(String productName) {
        try {
            WebElement qtyElement = driver.findElement(
                    By.xpath("//td[contains(text(),'" + productName + "')]/following-sibling::td[@class='cart_quantity']/button")
            );
            String qtyText = qtyElement.getText();
            return Integer.parseInt(qtyText);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Quantity not found for product: " + productName);
        }
    }

    // Get product subtotal as double
    public double getProductSubtotal(String productName) {
        try {
            WebElement subtotalElement = driver.findElement(
                    By.xpath("//td[contains(text(),'" + productName + "')]/following-sibling::td[@class='cart_total']")
            );
            String subtotalText = subtotalElement.getText(); // e.g., "Rs. 1000"
            subtotalText = subtotalText.replaceAll("[^0-9.]", "");
            return Double.parseDouble(subtotalText);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Subtotal not found for product: " + productName);
        }
    }

    public void clickProceedToCheckout() {
        try {
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a.btn.btn-default.check_out")  // matches: <a class="btn btn-default check_out">Proceed To Checkout</a>
            ));
            checkoutBtn.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Proceed To Checkout button is not clickable or not found.");
        }
    }


	
}
