package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonMethods;

import java.time.Duration;

public class HomePage extends CommonMethods {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Locators
    @FindBy(xpath = "//button[contains(text(),'✕')]")
    WebElement closePopup;

    @FindBy(name = "q")
    WebElement searchBox;

    @FindBy(xpath = "//span[text()='Electronics']")
    WebElement electronicsMenu;

    @FindBy(xpath = "//a[text()='Mobiles']")
    WebElement mobilesSubMenu;

    @FindBy(xpath = "//a[contains(@href,'mi-phones-store')]")
    WebElement miOption;


    // Constructor
    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    // Close popup if present
    public void closeLoginPopup() {
        try {
            safeClick(closePopup);
            logger.info("Popup closed");
        } catch (Exception e) {
            logger.info("No popup found");
        }
    }

    // Search product
    public void searchProduct(String product) {
        try {
            searchBox.sendKeys(product, Keys.ENTER);
            logger.info("Searched for product: {}", product);
        } catch (Exception e) {
            throw new RuntimeException("Unable to search for product: " + product);
        }
    }

    // Navigate Electronics -> Mobiles -> Mi
    public void navigateToMobiles() {
        try {
            Actions actions = new Actions(driver);

            // Hover on Electronics
            WebElement electronicsMenu = driver.findElement(By.xpath("//span[text()='Electronics']"));
            actions.moveToElement(electronicsMenu).perform();
            System.out.println("Hovered on Electronics menu");

            // Wait and click on Mobiles
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement mobilesLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[text()='Mobiles']")));
            mobilesLink.click();
            System.out.println("Clicked on Mobiles link");

        } catch (Exception e) {
            System.out.println("Failed to navigate to Mobiles: " + e.getMessage());
            throw new RuntimeException("Navigation to Mobiles failed: " + e.getMessage());
        }
    }

}
