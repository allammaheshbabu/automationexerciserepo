package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/path/to/chromedriver.exe");
        return new ChromeDriver();
    }
}
