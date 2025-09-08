package saucedemo.testclasses;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductPage;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeClass
    public void setupPages() {
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void validLoginTest() {
        loginPage.setUserName("standard_user");
        loginPage.setPassWord("secret_sauce");
        loginPage.clickLogin();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "User is not on Products page after login");
    }

    @Test
    public void invalidLoginTest() {
        loginPage.setUserName("wrong_user");
        loginPage.setPassWord("wrong_password");
        loginPage.clickLogin();

        String errorText = loginPage.error();
        Assert.assertTrue(errorText.contains("Username and password do not match any user"),
                "Error message not shown for invalid login");
    }

    @Test
    public void lockedOutUserTest() {
        loginPage.setUserName("locked_out_user");
        loginPage.setPassWord("secret_sauce");
        loginPage.clickLogin();

        String errorText = loginPage.error();
        Assert.assertTrue(errorText.contains("Epic sadface: Sorry, this user has been locked out."),
                "Locked out error message not displayed");
    }
}
