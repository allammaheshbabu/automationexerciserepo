package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigate to products page
    public void navigateToProductsPage() {
        driver.get("https://automationexercise.com/products");
    }

    // Click first product
    public void clickFirstProduct() {
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElement(By.xpath("(//a[contains(@href,'/product_details/')])[1]"))
        ));
        try {
            firstProduct.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstProduct);
        }
    }

    // Set quantity safely
    public void setQuantity(String quantity) {
        try {
            WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("quantity")));
            qtyInput.clear();
            qtyInput.sendKeys(quantity);
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println("Quantity input not found, using default 1");
        }
    }

    // Click "Add to cart"
    public void clickAddToCart() {
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.btn.btn-default.cart")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            addToCartButton.click();
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Add to cart button not found!");
        }
    }

    // Add first product with given quantity to cart
    public void addFirstProductToCart(String quantity) {
        clickFirstProduct();
        setQuantity(quantity);
        clickAddToCart();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
    }

    // Check if cart modal is visible
    public boolean isCartModalDisplayed() {
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
            return modal.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Go to cart
    public void goToCart() {
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view_cart']"))
        );
        cartLink.click();
    }

    // Proceed to checkout
    public void proceedToCheckout() {
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='btn btn-default check_out']"))
        );
        checkoutBtn.click();
    }

    // Place order with card details
    public void placeOrder(String cardNumber, String month, String year, String cvc) {
        WebElement nameOnCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name_on_card")));
        WebElement cardNum = driver.findElement(By.name("card_number"));
        WebElement expiryMonth = driver.findElement(By.name("cvc_month"));
        WebElement expiryYear = driver.findElement(By.name("cvc_year"));
        WebElement cvcInput = driver.findElement(By.name("cvc"));

        nameOnCard.sendKeys("Mahesh Babu");
        cardNum.sendKeys(cardNumber);
        expiryMonth.sendKeys(month);
        expiryYear.sendKeys(year);
        cvcInput.sendKeys(cvc);

        WebElement payBtn = driver.findElement(By.id("submit"));
        payBtn.click();
    }

    // Verify order success
    public boolean isOrderSuccessMessageVisible() {
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(text(),'Your order has been placed successfully!')]")
            ));
            return successMsg.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
