package saucedemo.pages;

import java.util.List;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage{

	public CartPage(WebDriver driver) {
		super(driver);
	}

	//Locators
	private By productNames = By.className("inventory_item_name");
	private By checkOut = By.id("checkout");
	private By removebtn = By.xpath("//button[contains(text(),'Remove')]");
	private By continueShoppingBtn = By.id("continue-shopping");
	
	//actions methods
	public List<WebElement> verifyAddedProducts(){
		List<WebElement> addedProducts = driver.findElements(productNames);
		return addedProducts;
	}
	
	 public int getCartItemCount() {
	        List<WebElement> products = driver.findElements(productNames);
	        return products.size();
	    }
	 
	 public void clickOnCheckOut() {
		 driver.findElement(checkOut).click();
	 }
	 
	 public void removeFromCart() {
		 List<WebElement> removeall = driver.findElements(removebtn);
		 for(WebElement remove:removeall) {
			 remove.click();
		 }
	 }
	 public void continueShopping() {
		 driver.findElement(continueShoppingBtn).click();
	 }
	 public void removeSingleProduct(String productName) {
		    List<WebElement> items = driver.findElements(By.className("cart_item"));
		    for (WebElement item : items) {
		        String name = item.findElement(By.className("inventory_item_name")).getText();
		        if (name.equalsIgnoreCase(productName)) {
		            item.findElement(By.xpath(".//button[contains(text(),'Remove')]")).click();
		            break;
		        }
		    }
		}

}
