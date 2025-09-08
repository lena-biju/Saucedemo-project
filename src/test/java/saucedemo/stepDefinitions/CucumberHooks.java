package saucedemo.stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CucumberHooks {
    
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before
    public void setUp() {
        // Initialize the WebDriver (ChromeDriver in this case)
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Ensure path is correct
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after each scenario
        }
    }
}
