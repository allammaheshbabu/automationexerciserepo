package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ContactUsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By nameInput = By.name("name");
    private By emailInput = By.name("email");
    private By subjectInput = By.name("subject");
    private By messageTextarea = By.name("message");
    private By submitButton = By.xpath("//input[@name='submit']");
    private By successMessage = By.xpath("//div[@class='status alert alert-success']");
    private By fileUploadInput = By.name("upload_file"); // Assuming input name for file

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToContactPage() {
        driver.get("https://automationexercise.com/contact_us");
    }

    public void fillContactForm(String name, String email, String subject, String message) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(subjectInput).sendKeys(subject);
        driver.findElement(messageTextarea).sendKeys(message);
    }

    public void uploadFile(String filePath) {
        driver.findElement(fileUploadInput).sendKeys(filePath);
    }

    public void submitContactForm() {
        driver.findElement(submitButton).click();

        // Handle alert if present
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            // No alert appeared, continue
        }
    }

    public boolean isSuccessMessageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
