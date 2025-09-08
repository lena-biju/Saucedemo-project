Feature: Login Page Tests

  Scenario: Verify URL is working
    Given I am on the login page
    Then I should see the logo "Swag Labs"

  Scenario: Invalid login attempt
    Given I am on the login page
    When I login with username "standard" and password "secret_sauce"
    Then I should see error "Epic sadface: Username and password do not match any user in this service"

  Scenario: Locked out user login
    Given I am on the login page
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I should see some error message

  Scenario: Valid login attempt
    Given I am on the login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to "https://www.saucedemo.com/inventory.html"
