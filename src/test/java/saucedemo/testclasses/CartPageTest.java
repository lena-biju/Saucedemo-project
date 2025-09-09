package saucedemo.testclasses;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import saucedemo.pages.CartPage;
import saucedemo.pages.ProductPage;
import saucedemo.utils.ScreenshotUtil;

public class CartPageTest extends BaseTest {

    private CartPage cartpage;
    private ProductPage productpage;

    @BeforeMethod
    public void init() {
        cartpage = new CartPage(driver);
        productpage = new ProductPage(driver);
    }

    @Test(priority = 1)
    public void isCartPage() {
        assertTrue(driver.getCurrentUrl().contains("cart.html"));
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "isCartPage");
    }

    @Test(priority = 2)
    public void removeProductFromCart() {
        productpage.removeAllFromCart();

        // Wait for the cart badge to disappear
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("shopping_cart_badge")));

        assertEquals(productpage.getCartCount(), 0);
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "removeProductFromCart");
    }

    @Test(priority = 4)
    public void clickCheckOut() {
        productpage.goToCart();
        cartpage.clickOnCheckOut();
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "clickCheckOut");
    }
}
