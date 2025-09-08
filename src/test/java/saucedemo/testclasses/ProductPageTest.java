package saucedemo.testclasses;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import saucedemo.pages.ProductPage;

public class ProductPageTest extends BaseTest {

    private ProductPage productpage;

    @BeforeMethod
    public void init() {
        productpage = new ProductPage(driver);
    }

    @Test(priority = 1)
    public void productDisplayed() {
        boolean displayed = productpage.productDetailsDisplayed();
        assertTrue(displayed);
    }

   

    @Test(priority = 2)  // TC012
    public void addProductToCart() throws InterruptedException {
        productpage.addAllToCart();
        assertEquals(productpage.getCartCount(), 6);
    }

   

    @Test(priority = 4)
    public void removeProductFromCart() {
        productpage.removeAllFromCart();
        
        // Wait for the cart badge to disappear
        new WebDriverWait(driver, Duration.ofSeconds(2))
            .until(ExpectedConditions.invisibilityOfElementLocated(By.className("shopping_cart_badge")));
        
        assertEquals(productpage.getCartCount(), 0);
    }


    @Test(priority = 5)
    public void addProductAfterRemovingAll() {
        productpage.removeAllFromCart();

        // Wait for cart to be cleared
        new WebDriverWait(driver, Duration.ofSeconds(2))
            .until(ExpectedConditions.invisibilityOfElementLocated(By.className("shopping_cart_badge")));

        // Add one product
        productpage.addSingleProduct("Sauce Labs Backpack");

        // Wait for cart count = 1
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "1"));

        assertEquals(productpage.getCartCount(), 1);
    }

    @Test(priority = 6)
    public void addSecondProductToCart() {
        productpage.removeAllFromCart();

        new WebDriverWait(driver, Duration.ofSeconds(2))
            .until(ExpectedConditions.invisibilityOfElementLocated(By.className("shopping_cart_badge")));
        assertEquals(productpage.getCartCount(), 0);

        productpage.addSingleProduct("Sauce Labs Backpack");
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "1"));
        assertEquals(productpage.getCartCount(), 1);

        productpage.addSingleProduct("Sauce Labs Bike Light");
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "2"));
        assertEquals(productpage.getCartCount(), 2);
    }



    @Test(priority = 7)
    public void goToCartTest() throws InterruptedException {
        productpage.addAllToCart();
        productpage.goToCart();
       
        String actual = driver.getCurrentUrl();
        assertTrue("https://www.saucedemo.com/cart.html".equalsIgnoreCase(actual));
    }
}
