package saucedemo.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import saucedemo.utils.ScreenshotUtil;

public class BasePage {

	 protected WebDriver driver;   // protected = accessible in subclasses
	 protected WebDriverWait wait;
	    
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver,Duration.ofSeconds(2));
	}
	protected void captureScreenshot(String testName) {
	    ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), testName);
	}

}
