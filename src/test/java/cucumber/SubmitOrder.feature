Feature: Purchase the order from Ecommerce Website

  Background:
    Given I landed on the Ecommerce Page

  @Regression
  Scenario Outline: Positive Test for Submitting the Order
    Given I Logged in with email <email> and password <password>
    When I add product <productName> to the Cart
    And Checkout <productName> and submit the Order
    Then <confirmMessage> message is displayed on Confirmation Page
    And I should see the Order on Order History Page
    Examples:
      | email   | password   | productName   | confirmMessage   |
      | email_1 | password_1 | productName_1 | confirmMessage_1 |
