package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickPlaceOrder() {
        WebElement placeOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/payment']")));
        placeOrderBtn.click();
    }

    public void enterCardDetails(String name, String cardNo, String cvc, String mm, String yyyy) {
        driver.findElement(By.name("name_on_card")).sendKeys(name);
        driver.findElement(By.name("card_number")).sendKeys(cardNo);
        driver.findElement(By.name("cvc")).sendKeys(cvc);
        driver.findElement(By.name("expiry_month")).sendKeys(mm);
        driver.findElement(By.name("expiry_year")).sendKeys(yyyy);
    }

    public void clickPayAndConfirm() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success"))).getText();
    }
}
