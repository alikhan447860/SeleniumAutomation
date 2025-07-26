package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {
    WebDriver driver;

    // Locators
    By cartIcon = By.xpath("//a[contains(@href,'/viewcart')]");
    By loginButton = By.xpath("//a[contains(@href,'account/login')]");

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Open cart
    public void openCart() {
        driver.findElement(cartIcon).click();
        System.out.println("Cart opened");
    }

    // Get login button text
    public String getLoginButtonText() {
        return driver.findElement(loginButton).getText().trim();
    }

    // Click login
    public void clickLogin() {
        driver.findElement(loginButton).click();
        System.out.println("Login clicked");
    }
}
