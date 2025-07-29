package utils;

import org.openqa.selenium.WebDriver;
import stepdefinitions.Hooks;

public class CommonMethods {
    protected static WebDriver driver;

    public CommonMethods() {
        driver = Hooks.driver; // Hooks ka driver le lo
    }
}
