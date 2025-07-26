package com.demo.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class YouTubeSearchTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com");

        // Search box locate karo
        WebElement searchBox = driver.findElement(By.name("search_query"));
        searchBox.sendKeys("Selenium tutorial");
        searchBox.submit();

        // Title print karo
        Assert.assertEquals("YouTube",driver.getTitle());
        driver.quit();
    }
}
