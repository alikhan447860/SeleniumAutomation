package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import pages.CartPage;
import utils.AssertionUtil;

public class FlipkartSteps {

    private static final Logger logger = LoggerFactory.getLogger(FlipkartSteps.class);

    WebDriver driver = Hooks.driver;  // Hooks se driver le lo
    HomePage home;
    CartPage cart;

    @Given("user is on Flipkart homepage")
    public void user_is_on_flipkart_homepage() {
        driver.get("https://www.flipkart.com");
        logger.info("Navigated to Flipkart homepage");
        home = new HomePage();
        cart = new CartPage();
        home.closeLoginPopup();
    }

    @When("user searches for {string}")
    public void user_searches_for(String product) {
        home.searchProduct(product);
        logger.info("Searched for product: {}", product);
    }

    @Then("user goes to cart and clicks login button")
    public void user_goes_to_cart_and_clicks_login_button() {
        try {
            cart.openCart();
            String btnText = cart.getLoginButtonText();
            AssertionUtil.assertEquals("Verify login button text on Cart Page", "Login", btnText);
            cart.clickLogin();
            logger.info("Navigated to cart and clicked login");
        } catch (Exception e) {
            logger.error("Failed at cart/login step: {}", e.getMessage());
            AssertionUtil.fail("Failed at cart/login step: " + e.getMessage());

        }
    }
}
