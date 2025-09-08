package stepDefinitions;

import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import utils.DriverFactory;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Hooks {

    public static WebDriver driver;
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;

    @Before
    public void setup(Scenario scenario) {
        // WebDriver init
        driver = DriverFactory.createDriver(); // <- make your DriverFactory return ChromeDriver
        driver.manage().window().maximize();

        // Start Extent Test
        test = extent.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capture Screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String screenshotPath = System.getProperty("user.dir") + "/reports/screenshots/" 
                        + scenario.getName().replaceAll(" ", "_") + ".png";
                Files.createDirectories(new File(screenshotPath).getParentFile().toPath());
                Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());

                // Attach screenshot to Extent report
                test.fail("Test Failed, see screenshot").addScreenCaptureFromPath(screenshotPath);

                // Attach screenshot to Cucumber report too
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(bytes, "image/png", "Failed Screenshot");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            test.pass("Test Passed ✅");
        }

        driver.quit();
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
