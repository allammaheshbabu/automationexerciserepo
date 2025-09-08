package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.ContactUsPage;

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

    @Test
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
        System.out.println("Test case 11 passed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
