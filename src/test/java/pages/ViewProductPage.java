package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewProductPage {
    private WebDriver driver;

    private By quantityInput = By.id("quantity");
    private By addToCartBtn = By.xpath("//button[contains(@class,'cart')]");
    private By modalCart = By.id("cartModal"); // Optional modal id

    public ViewProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setQuantity(int qty) {
        WebElement quantity = driver.findElement(quantityInput);
        quantity.clear();
        quantity.sendKeys(String.valueOf(qty));
    }

    public int getQuantity() {
        return Integer.parseInt(driver.findElement(quantityInput).getAttribute("value"));
    }

    public void clickAddToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public boolean isCartModalDisplayed() {
        return driver.findElements(modalCart).size() > 0;
    }
}
