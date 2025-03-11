Feature: Error Validation

  @ErrorValidations
  Scenario Outline: Error Validation Test with incorrect Email & Password
    Given I landed on the Ecommerce Page
    When I Logged in with email <email> and password <password>
    Then <errorMessage> message is displayed
    Examples:
      | email   | password   | errorMessage |
      | email_3 | password_3 | errorMessage_3 |
      | email_4 | password_4 | errorMessage_4 |
