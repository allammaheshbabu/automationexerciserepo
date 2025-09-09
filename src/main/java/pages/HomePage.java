package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

public class HomePage {
    private WebDriver driver;
    private By logoutLink = By.xpath("//a[contains(text(),'Logout')]");
    private By loggedInAs = By.xpath("//a[contains(text(),'Logged in as')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isUserLoggedIn() {
        return driver.findElements(loggedInAs).size() > 0;
    }

    public void logout() {
        driver.findElement(logoutLink).click();
    }
}
