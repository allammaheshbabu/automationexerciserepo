package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import utils.TestData;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test(priority = 4)
    public void testLoginPositive() {
        System.out.println("Registered Email: " + TestData.registeredEmail);
        System.out.println("Registered Password: " + TestData.registeredPassword);

        if (TestData.registeredEmail != null && TestData.registeredPassword != null) {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(TestData.registeredEmail, TestData.registeredPassword);

            // Wait until "Logged in as" is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(),'Logged in as')]"))
            );

            HomePage homePage = new HomePage(driver);
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in with valid credentials");
            System.out.println("Login successful");
            System.out.println("test case  4 passed");
            

            // Optional: logout to keep tests independent
            homePage.logout();
        } else {
            System.out.println("Dynamic credentials not available. Skipping login.");
        }
    }

    @Test(priority = 5)
    public void testLoginNegative() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrong@gmail.com", "WrongPass");

        HomePage homePage = new HomePage(driver);
        // Pass if user is NOT logged in (expected behavior)
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should NOT be logged in with wrong password");
        System.out.println("test case  5 passed");
    }

    @Test(priority = 6)
    public void testLoginEmpty() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        HomePage homePage = new HomePage(driver);
        // Pass if user is NOT logged in (expected behavior)
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should NOT be logged in with empty credentials");
        System.out.println("test case  6 passed");
    }
}
