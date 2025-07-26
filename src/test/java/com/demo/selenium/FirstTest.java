package com.demo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    public static void main(String[] args) {
        // Step 1: Browser open
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");

        // Step 2: Search box locate karo aur text bhejo
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");

        // Step 3: Submit karo
        searchBox.submit();

        // Step 4: Title print karo
        System.out.println("Page Title: " + driver.getTitle());

        // Step 5: Close browser
        driver.quit();
    }
}
