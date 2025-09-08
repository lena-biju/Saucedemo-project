package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutPage extends BasePage {

    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private By firstName_field = By.id("first-name");
    private By lastName_field = By.id("last-name");
    private By zipCode_field = By.id("postal-code");
    private By continuebtn = By.id("continue");
    private By overView = By.xpath("//span[@class='title']");
    private By finishbtn = By.id("finish");
    private By orderMessage = By.cssSelector(".complete-header");

    // Actions
    public String getHeadingText() {
        return driver.findElement(overView).getText();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstName_field).clear();
        driver.findElement(firstName_field).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastName_field).clear();
        driver.findElement(lastName_field).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        driver.findElement(zipCode_field).clear();
        driver.findElement(zipCode_field).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continuebtn).click();
    }

    public void clickFinish() {
        driver.findElement(finishbtn).click();
    }

    public String getOrderMessage() {
        return driver.findElement(orderMessage).getText();
    }
}
