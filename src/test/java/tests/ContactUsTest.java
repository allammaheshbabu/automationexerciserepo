package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ContactUsTest {

    private WebDriver driver;
    private ContactUsPage contactUsPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        contactUsPage = new ContactUsPage(driver);
    }
    @Test(priority = 13)
    public void verifyContactUsPageLoad() {
        // Navigate to Contact Us page
        driver.get("https://automationexercise.com/contact_us");

        // Get the page title
        String actualTitle = driver.getTitle();
        System.out.println("Contact Us Page Title: " + actualTitle);

        // Expected title
        String expectedTitle = "Automation Exercise - Contact Us";

        // Verify title
        Assert.assertEquals(actualTitle, expectedTitle,
                "Contact Us page title mismatch!");

        System.out.println("Contact Us page loaded successfully with correct title.");
        System.out.println("Test case 13 passed");
    }
    @Test(priority = 14)
    public void testSubmitContactFormWithFile() {
        contactUsPage.navigateToContactPage();

        contactUsPage.fillContactForm(
                "Mahesh Babu",
                "maheshbabu23@example.com",
                "Test Subject",
                "This is a test message for automation."
        );

        // Upload PDF
        contactUsPage.uploadFile("C:/Users/allam/OneDrive/Desktop/Java_String_Class_Guide.pdf");

        // Submit form
        contactUsPage.submitContactForm();

        // Verify success message
        Assert.assertTrue(contactUsPage.isSuccessMessageVisible(),
                "Contact form success message is not visible");
        System.out.println("Test case 14 passed");
    }
    @Test(priority = 15)
    public void verifyYouTubeChannelLoad() {
        // Navigate to Automation Exercise YouTube Channel
        driver.get("https://www.youtube.com/c/AutomationExercise");

        // Get the page title
        String actualTitle = driver.getTitle();
        System.out.println("YouTube Channel Page Title: " + actualTitle);

        // Expected title will contain "Automation Exercise"
        String expectedTitleKeyword = "AutomationExercise - YouTube";

        // Verify title contains keyword
        Assert.assertTrue(actualTitle.contains(expectedTitleKeyword),
                "YouTube channel title does not contain expected keyword!");

        System.out.println(" YouTube channel loaded successfully with correct title.");
        System.out.println("Test case 15 passed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
