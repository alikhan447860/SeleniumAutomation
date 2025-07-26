package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    WebDriver driver;

    // Locators
    By closePopup = By.xpath("//button[contains(text(),'✕')]");
    By searchBox = By.name("q");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Close popup if exists
    public void closeLoginPopup() {
        try {
            WebElement popup = driver.findElement(closePopup);
            popup.click();
            System.out.println("Popup closed");
        } catch (Exception e) {
            System.out.println("No popup found");
        }
    }

    // Search product
    public void searchProduct(String product) {
        driver.findElement(searchBox).sendKeys(product, Keys.ENTER);
        System.out.println("Searched for: " + product);
    }
}
