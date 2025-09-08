package saucedemo.stepDefinitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.*;
import saucedemo.pages.LoginPage;


public class LoginPageSteps {
    
    private WebDriver driver = CucumberHooks.getDriver(); // Get the driver from CucumberHooks
    private LoginPage loginPage;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Then("I should see the logo {string}")
    public void i_should_see_the_logo(String expected) {
        String actual = loginPage.logo();
        assertEquals(actual, expected);
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String user, String pass) {
        loginPage.setUserName(user);
        loginPage.setPassWord(pass);
        loginPage.clickLogin();
    }

    @Then("I should see error {string}")
    public void i_should_see_error(String expectedError) {
        String actualError = loginPage.error();
        assertEquals(actualError, expectedError);
    }

    @Then("I should see some error message")
    public void i_should_see_some_error_message() {
        assertNotNull(loginPage.error());
    }

    @Then("I should be redirected to {string}")
    public void i_should_be_redirected_to(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl);
    }
}
