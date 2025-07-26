package com.demo.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlipkartTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com");

        // Close login popup if appears
        try {
            WebElement closeBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button._2KpZ6l._2doB4z")));
            closeBtn.click();
        } catch (Exception e) {
            System.out.println("No popup.");
        }

        // Search for iphone
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("iphone");
        searchBox.submit();

        // Wait for product titles to appear
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._4rR01T")));

        // Assertion: Check if search box still contains "iphone"
        WebElement searchInput = driver.findElement(By.name("q"));
        Assert.assertTrue("Search box does not contain 'iphone'", searchInput.getAttribute("value").toLowerCase().contains("iphone"));
        System.out.println("Assertion Passed: Search performed for 'iphone'");

        driver.quit();
    }
}
