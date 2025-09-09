package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

public class LoginPage {
    private WebDriver driver;

    // Correct locators
    private By emailField = By.xpath("//input[@data-qa='login-email']");
    private By passwordField = By.xpath("//input[@data-qa='login-password']");
    private By loginButton = By.xpath("//button[@data-qa='login-button']");
    private By loginError = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");
    private By logoutLink = By.xpath("//a[contains(text(),'Logout')]"); // ✅ success indicator

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginErrorDisplayed() {
        return driver.findElements(loginError).size() > 0;
    }

    public boolean isLoginSuccessful() {
        return driver.findElements(logoutLink).size() > 0;
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
