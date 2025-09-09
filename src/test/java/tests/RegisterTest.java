package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
import pages.HomePage;
import utils.TestData;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class RegisterTest extends BaseTest {

	
	 @Test(priority = 1)
	 public void verifySignupPageTitle() {
	        // Navigate to signup page
	        driver.get("https://automationexercise.com/signup");

	        // Get page title
	        String actualTitle = driver.getTitle();
	        System.out.println("Signup Page Title: " + actualTitle);

	        // Expected title (observed from the site)
	        String expectedTitle = "Automation Exercise - Signup / Login";

	        // Assertion
	        Assert.assertEquals(actualTitle, expectedTitle,
	                "Signup page title mismatch! Page not loaded correctly.");

	        System.out.println("Signup page loaded successfully with correct title.");
	        System.out.println("Test case 1 passed");
	    }
	@Test(priority = 2)
	public void testRegisterPositive() {
	    RegisterPage registerPage = new RegisterPage(driver);

	    // Generate unique email
	    String email = "mahesh" + System.currentTimeMillis() + "@test.com";
	    String password = "Test@123";

	    registerPage.startSignup("Mahesh123", email);

	    registerPage.fillSignupForm(
	            password, "Mahesh", "Babu",
	            "Hyd Addr", "Telangana", "Hyd",
	            "500001", "9876543210",
	            "10", "May", "1995"
	    );

	    registerPage.clickContinueAfterSignup();

	    // Wait until "Logged in as" appears
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));

	    HomePage homePage = new HomePage(driver);
	    Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in after registration");

	    System.out.println("Registration + login successful");

	    // Save dynamic credentials
	    TestData.registeredEmail = email;
	    TestData.registeredPassword = password;

	    // 🔹 Logout after successful registration
	    homePage.logout();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Signup / Login')]")));
	    System.out.println("User logged out after registration");
	    System.out.println("test case  2 passed");
	}


    @Test(priority = 3)
    public void testRegisterNegative() {
        RegisterPage registerPage = new RegisterPage(driver);

        // Use a static already-registered email
        registerPage.startSignup("Mahesh", "mahesh@test.com");

        // Assert error message appears
        Assert.assertTrue(registerPage.isEmailAlreadyRegisteredErrorVisible(),
                "Expected error for already registered email");
        System.out.println("test case  3 passed");
    }

    @Test(priority = 4)
    public void testRegisterEmptyFields() {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.startSignup("", "");

        // Assert signup button is still visible (expected behavior)
        Assert.assertTrue(registerPage.isSignupButtonVisible(),
                "Expected behavior: signup button remains visible for empty fields");
        System.out.println("test case  4 passed");
    }
}
