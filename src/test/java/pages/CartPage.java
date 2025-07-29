package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonMethods;

public class CartPage extends CommonMethods {

    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);

    // Locators with @FindBy
    @FindBy(xpath = "//a[contains(@href,'/viewcart')]")
    WebElement cartIcon;

    @FindBy(xpath = "//a[contains(@href,'account/login')]")
    WebElement loginButton;

    // Constructor
    public CartPage() {
        PageFactory.initElements(driver, this);
    }

    // Open cart
    public void openCart() {
        cartIcon.click();
        logger.info("Cart open ");
    }

    // Get login button text
    public String getLoginButtonText() {
        return loginButton.getText().trim();
    }

    // Click login
    public void clickLogin() {
        loginButton.click();
        logger.info("Login clicked");
    }
}
