package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stepdefinitions.Hooks;

import java.time.Duration;

public class CommonMethods {
    protected static WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(CommonMethods.class);
    private WebDriverWait wait;

    public CommonMethods() {
        driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15s wait
    }

    // Wait for element visible
    public WebElement waitForVisibility(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible: " + element);
        }
    }

    // Scroll into view
    public void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Scroll failed: {}", e.getMessage());
        }
    }

    // Safe click (scroll + wait + normal + JS click if needed)
    public void safeClick(WebElement element) {
        try {
            WebElement el = waitForVisibility(element);
            scrollIntoView(el);
            try {
                el.click();
            } catch (Exception ex) {
                logger.info("Normal click failed, trying JS click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            }
            logger.info("Clicked element: {}", el.getText());
        } catch (Exception e) {
            logger.error("Click failed: {}", e.getMessage());
            throw new RuntimeException("Unable to click element");
        }
    }

    // Hover on element
    public void hoverOnElement(WebElement element) {
        try {
            WebElement el = waitForVisibility(element);
            Actions actions = new Actions(driver);
            actions.moveToElement(el).perform();
            logger.info("Hovered on element: {}", el.getText());
        } catch (Exception e) {
            throw new RuntimeException("Unable to hover: " + e.getMessage());
        }
    }

    // Click using Actions
    public void clickUsingActions(WebElement element) {
        try {
            WebElement el = waitForVisibility(element);
            Actions actions = new Actions(driver);
            actions.moveToElement(el).click().perform();
            logger.info("Clicked using Actions on element: {}", el.getText());
        } catch (Exception e) {
            throw new RuntimeException("Unable to click using Actions: " + e.getMessage());
        }
    }

    // Double click
    public void doubleClickElement(WebElement element) {
        try {
            WebElement el = waitForVisibility(element);
            Actions actions = new Actions(driver);
            actions.doubleClick(el).perform();
            logger.info("Double clicked on element: {}", el.getText());
        } catch (Exception e) {
            throw new RuntimeException("Unable to double click: " + e.getMessage());
        }
    }

    // Drag and drop
    public void dragAndDropElement(WebElement source, WebElement target) {
        try {
            WebElement src = waitForVisibility(source);
            WebElement tgt = waitForVisibility(target);
            Actions actions = new Actions(driver);
            actions.dragAndDrop(src, tgt).perform();
            logger.info("Dragged element from {} to {}", src.getText(), tgt.getText());
        } catch (Exception e) {
            throw new RuntimeException("Unable to drag and drop: " + e.getMessage());
        }
    }

    // Right click
    public void rightClickElement(WebElement element) {
        try {
            WebElement el = waitForVisibility(element);
            Actions actions = new Actions(driver);
            actions.contextClick(el).perform();
            logger.info("Right clicked on element: {}", el.getText());
        } catch (Exception e) {
            throw new RuntimeException("Unable to right click: " + e.getMessage());
        }
    }
}
