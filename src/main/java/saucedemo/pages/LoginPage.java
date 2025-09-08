package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
	
	//constructor
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	//locators
	private By field_username = By.id("user-name");
	private By field_password = By.id("password");
	private By btn_login = By.id("login-button");
	private By logo = By.xpath("//div[@class='login_logo']");
	private By field_error = By.xpath("//h3[@data-test='error']");
	
	//actions
	public void setUserName(String username) {
		driver.findElement(field_username).sendKeys(username);
	}
	
	public void setPassWord(String password) {
		driver.findElement(field_password).sendKeys(password);
	}
	
	public void clickLogin() {
		driver.findElement(btn_login).click();
	}
	
	public String logo() {
		return driver.findElement(logo).getText();
	}
	public String error() {
		String error = driver.findElement(field_error).getText();
		if(error!=null) {
			return error;
		}
		else {
			return "";
		}
	}
	
}
