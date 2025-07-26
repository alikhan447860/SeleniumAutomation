package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.CartPage;

public class FlipkartSteps {
    WebDriver driver;
    HomePage home;
    CartPage cart;

    @Given("user is on Flipkart homepage")
    public void user_is_on_flipkart_homepage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com");
        home = new HomePage(driver);
        cart = new CartPage(driver);
        home.closeLoginPopup();
    }

    @When("user searches for {string}")
    public void user_searches_for(String product) {
        home.searchProduct(product);
    }

    @Then("user goes to cart and clicks login button")
    public void user_goes_to_cart_and_clicks_login_button() {
        try {
            cart.openCart();
            String btnText = cart.getLoginButtonText();
            Assert.assertEquals("Login", btnText);
            cart.clickLogin();
        } catch (Exception e) {
            Assert.fail("Failed at cart/login step: " + e.getMessage());
        }
    }
}
