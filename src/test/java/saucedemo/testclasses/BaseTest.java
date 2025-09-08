package saucedemo.testclasses;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;



public class BaseTest {
	 protected static WebDriver driver;
	 WebDriverWait wait;
	 
	    @BeforeSuite
	    public void setup() {
	    	ChromeOptions options = new ChromeOptions();
	    	options.addArguments("--disable-features=PasswordLeakDetection");
	    	options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
	            put("profile.password_manager_leak_detection", false);
	            put("credentials_enable_service", false); // Disables "Save Password" prompt as well
	        }});
	        driver = new ChromeDriver(options);
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.get("https://www.saucedemo.com/");
	       wait = new WebDriverWait(driver,Duration.ofSeconds(2));
	    }

	    @AfterSuite
	    public void teardown() {
	        if(driver != null) {
	            driver.quit();
	        }
	    }
	    
	    
}




