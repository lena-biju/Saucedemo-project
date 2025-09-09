package saucedemo.testclasses;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import saucedemo.pages.CheckOutPage;
import saucedemo.utils.ScreenshotUtil;

public class CheckOutPageTest extends BaseTest {

    private CheckOutPage checkoutPage;

    @BeforeMethod
    public void init() {
        checkoutPage = new CheckOutPage(driver);
    }

    @Test(priority = 1)
    public void verifyCheckoutPageHeading() {
        String heading = checkoutPage.getHeadingText();
        assertEquals(heading, "Checkout: Your Information", "Checkout page heading mismatch");
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "verifyCheckoutPageHeading");
    }

    @Test(priority = 2)
    public void fillCheckoutInformation() throws InterruptedException {
        checkoutPage.enterFirstName("John");
        Thread.sleep(2000);
        checkoutPage.enterLastName("Doe");
        Thread.sleep(2000);
        checkoutPage.enterPostalCode("12345");
        Thread.sleep(2000);
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "fillCheckoutInformation");
    }

    @Test(priority = 3)
    public void clickContinueAndVerifyNavigation() {
        checkoutPage.clickContinue();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("checkout-step-two.html"), "Did not navigate to checkout step two");
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "clickContinueAndVerifyNavigation");
    }

    @Test(priority = 4)
    public void completeCheckoutProcess() throws InterruptedException {
        Thread.sleep(2000);
        checkoutPage.clickFinish();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("checkout-complete.html"), "Did not complete checkout successfully");
        ScreenshotUtil.takeScreenshot(driver, this.getClass().getSimpleName(), "completeCheckoutProcess");
    }
}
