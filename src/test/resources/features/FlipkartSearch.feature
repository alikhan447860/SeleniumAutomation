@regression @smoke
Feature: Flipkart Search and cart

  @critical @smoke
  Scenario: Search in Flipkart
    Given user is on Flipkart homepage
    When user searches for "Products"
    And user navigates to mobiles under Electronics
    Then user goes to cart and clicks login button
