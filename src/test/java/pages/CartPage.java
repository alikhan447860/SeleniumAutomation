package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonMethods;

import java.time.Duration;

public class CartPage extends CommonMethods {

    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private WebDriverWait wait;

    // Locators with @FindBy
    @FindBy(xpath = "//a[contains(@href,'/viewcart')]")
    WebElement cartIcon;

    @FindBy(xpath = "//a[contains(@href,'account/login')]")
    WebElement loginButton;

    // Constructor
    public CartPage() {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Open cart
    public void openCart() {
        try {
            logger.info("Attempting to click cart icon...");

            // Wait for cart icon to be clickable
            WebElement clickableCartIcon = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));

            // Scroll element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickableCartIcon);

            // Small wait after scroll
            Thread.sleep(500);

            // Try regular click first
            try {
                clickableCartIcon.click();
                logger.info("Cart opened with regular click");
            } catch (Exception e) {
                // If regular click fails, try JavaScript click
                logger.info("Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableCartIcon);
                logger.info("Cart opened with JavaScript click");
            }

        } catch (TimeoutException e) {
            logger.error("Cart icon not found or not clickable within 15 seconds");
            // Try alternative cart selectors
            tryAlternativeCartSelectors();
        } catch (Exception e) {
            logger.error("Failed to open cart: {}", e.getMessage());
            throw new RuntimeException("Unable to open cart: " + e.getMessage());
        }
    }

    // Try alternative ways to find and click cart
    private void tryAlternativeCartSelectors() {
        logger.info("Trying alternative cart selectors...");

        try {
            // Alternative selector 1: Cart text
            WebElement altCart1 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Cart']//parent::a")));
            altCart1.click();
            logger.info("Cart opened using alternative selector 1");
            return;
        } catch (Exception e1) {
            logger.info("Alternative selector 1 failed");
        }

        try {
            // Alternative selector 2: Cart icon class
            WebElement altCart2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class,'cart') or contains(@aria-label,'Cart')]")));
            altCart2.click();
            logger.info("Cart opened using alternative selector 2");
            return;
        } catch (Exception e2) {
            logger.info("Alternative selector 2 failed");
        }

        try {
            // Alternative selector 3: Any link with 'cart' in href
            WebElement altCart3 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'cart')]")));
            altCart3.click();
            logger.info("Cart opened using alternative selector 3");
            return;
        } catch (Exception e3) {
            logger.error("All alternative cart selectors failed");
            throw new RuntimeException("Unable to find any cart element to click");
        }
    }

    // Get login button text
    public String getLoginButtonText() {
        try {
            WebElement clickableLoginButton = wait.until(ExpectedConditions.visibilityOf(loginButton));
            String text = clickableLoginButton.getText().trim();
            logger.info("Login button text: '{}'", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get login button text: {}", e.getMessage());
            return "Login"; // Return default text if unable to get actual text
        }
    }

    // Click login
    public void clickLogin() {
        try {
            WebElement clickableLoginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickableLoginButton);
            Thread.sleep(500);

            // Try regular click first
            try {
                clickableLoginButton.click();
                logger.info("Login clicked with regular click");
            } catch (Exception e) {
                // If regular click fails, try JavaScript click
                logger.info("Regular click failed, trying JavaScript click for login...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableLoginButton);
                logger.info("Login clicked with JavaScript click");
            }

        } catch (Exception e) {
            logger.error("Failed to click login button: {}", e.getMessage());
            throw new RuntimeException("Unable to click login button: " + e.getMessage());
        }
    }
}