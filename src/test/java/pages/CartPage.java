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
import java.util.List;

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

    // Open cart - Debug version to find available elements
    public void openCart() {
        try {
            logger.info("Attempting to find cart elements...");

            // First, let's see what cart-related elements exist on the page
            debugAvailableCartElements();

            // Try to find any cart element
            WebElement cartElement = findAnyCartElement();

            if (cartElement != null) {
                // Scroll element into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartElement);
                Thread.sleep(500);

                // Try clicking
                try {
                    cartElement.click();
                    logger.info("Cart opened successfully");
                } catch (Exception e) {
                    logger.info("Regular click failed, trying JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartElement);
                    logger.info("Cart opened with JavaScript click");
                }
            } else {
                logger.error("No cart element found on the page");
                throw new RuntimeException("Unable to find any cart element");
            }

        } catch (Exception e) {
            logger.error("Failed to open cart: {}", e.getMessage());
            throw new RuntimeException("Unable to open cart: " + e.getMessage());
        }
    }

    // Debug method to see what cart elements are available
    private void debugAvailableCartElements() {
        logger.info("=== DEBUGGING AVAILABLE CART ELEMENTS ===");

        try {
            // Check for any elements containing "cart" text
            List<WebElement> cartTextElements = driver.findElements(By.xpath("//*[contains(text(),'Cart') or contains(text(),'cart')]"));
            logger.info("Found {} elements with 'Cart' text", cartTextElements.size());
            for (int i = 0; i < Math.min(cartTextElements.size(), 5); i++) {
                try {
                    WebElement el = cartTextElements.get(i);
                    logger.info("Cart text element {}: tag={}, text='{}', displayed={}",
                            i+1, el.getTagName(), el.getText(), el.isDisplayed());
                } catch (Exception e) {
                    logger.info("Cart text element {} error: {}", i+1, e.getMessage());
                }
            }

            // Check for any links containing "cart" in href
            List<WebElement> cartHrefElements = driver.findElements(By.xpath("//a[contains(@href,'cart')]"));
            logger.info("Found {} elements with 'cart' in href", cartHrefElements.size());
            for (int i = 0; i < Math.min(cartHrefElements.size(), 5); i++) {
                try {
                    WebElement el = cartHrefElements.get(i);
                    logger.info("Cart href element {}: href='{}', text='{}', displayed={}",
                            i+1, el.getAttribute("href"), el.getText(), el.isDisplayed());
                } catch (Exception e) {
                    logger.info("Cart href element {} error: {}", i+1, e.getMessage());
                }
            }

            // Check for elements with cart-related attributes
            List<WebElement> cartAttrElements = driver.findElements(By.xpath("//*[contains(@class,'cart') or contains(@id,'cart') or contains(@data-testid,'cart')]"));
            logger.info("Found {} elements with cart-related attributes", cartAttrElements.size());
            for (int i = 0; i < Math.min(cartAttrElements.size(), 5); i++) {
                try {
                    WebElement el = cartAttrElements.get(i);
                    logger.info("Cart attr element {}: tag={}, class='{}', id='{}', displayed={}",
                            i+1, el.getTagName(), el.getAttribute("class"), el.getAttribute("id"), el.isDisplayed());
                } catch (Exception e) {
                    logger.info("Cart attr element {} error: {}", i+1, e.getMessage());
                }
            }

            // Check for SVG cart icons
            List<WebElement> cartSvgElements = driver.findElements(By.xpath("//svg[contains(@class,'cart')] | //*[contains(@class,'cart-icon')] | //*[contains(@aria-label,'cart')]"));
            logger.info("Found {} SVG/icon cart elements", cartSvgElements.size());
            for (int i = 0; i < Math.min(cartSvgElements.size(), 3); i++) {
                try {
                    WebElement el = cartSvgElements.get(i);
                    logger.info("Cart SVG element {}: tag={}, class='{}', aria-label='{}', displayed={}",
                            i+1, el.getTagName(), el.getAttribute("class"), el.getAttribute("aria-label"), el.isDisplayed());
                } catch (Exception e) {
                    logger.info("Cart SVG element {} error: {}", i+1, e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.error("Error during cart elements debugging: {}", e.getMessage());
        }

        logger.info("=== END CART ELEMENTS DEBUG ===");
    }

    // Try to find any cart element using multiple strategies
    private WebElement findAnyCartElement() {
        logger.info("Trying to find any cart element...");

        // Strategy 1: Look for visible cart text
        try {
            List<WebElement> cartElements = driver.findElements(By.xpath("//*[contains(text(),'Cart') and @tag-name!='title']"));
            for (WebElement el : cartElements) {
                if (el.isDisplayed() && el.isEnabled()) {
                    logger.info("Found clickable cart text element: {}", el.getText());
                    return el;
                }
            }
        } catch (Exception e) {
            logger.info("Strategy 1 failed: {}", e.getMessage());
        }

        // Strategy 2: Look for cart links
        try {
            List<WebElement> cartLinks = driver.findElements(By.xpath("//a[contains(@href,'cart')]"));
            for (WebElement el : cartLinks) {
                if (el.isDisplayed() && el.isEnabled()) {
                    logger.info("Found clickable cart link: href={}", el.getAttribute("href"));
                    return el;
                }
            }
        } catch (Exception e) {
            logger.info("Strategy 2 failed: {}", e.getMessage());
        }

        // Strategy 3: Look for elements with cart in class or id
        try {
            List<WebElement> cartAttr = driver.findElements(By.xpath("//*[contains(@class,'cart') or contains(@id,'cart')]"));
            for (WebElement el : cartAttr) {
                if (el.isDisplayed() && el.isEnabled()) {
                    logger.info("Found clickable cart attribute element: class={}, id={}",
                            el.getAttribute("class"), el.getAttribute("id"));
                    return el;
                }
            }
        } catch (Exception e) {
            logger.info("Strategy 3 failed: {}", e.getMessage());
        }

        // Strategy 4: Look for shopping bag or basket icons (alternative terms)
        try {
            List<WebElement> bagElements = driver.findElements(By.xpath("//*[contains(text(),'Bag') or contains(@aria-label,'bag') or contains(@aria-label,'Bag')]"));
            for (WebElement el : bagElements) {
                if (el.isDisplayed() && el.isEnabled()) {
                    logger.info("Found clickable bag element: text={}, aria-label={}",
                            el.getText(), el.getAttribute("aria-label"));
                    return el;
                }
            }
        } catch (Exception e) {
            logger.info("Strategy 4 failed: {}", e.getMessage());
        }

        logger.error("No cart element found with any strategy");
        return null;
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