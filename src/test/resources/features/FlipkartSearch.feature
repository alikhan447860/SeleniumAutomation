Feature: Flipkart Search and cart

  Scenario: Search in Flipkart
    Given user is on Flipkart homepage
    When user searches for "Products"
    Then user goes to cart and clicks login button
