package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonMethods;

public class HomePage extends CommonMethods {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Locators with @FindBy
    @FindBy(xpath = "//button[contains(text(),'✕')]")
    WebElement closePopup;

    @FindBy(name = "q")
    WebElement searchBox;

    // Constructor
    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    // Close popup if exists
    public void closeLoginPopup() {
        try {
            closePopup.click();
            logger.info("Popup closed");
        } catch (Exception e) {
            logger.info("No popup found");
        }
    }

    // Search product
    public void searchProduct(String product) {
        searchBox.sendKeys(product, Keys.ENTER);
        logger.info("Searched for product: {}", product);
    }
}
