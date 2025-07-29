package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import utils.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    public static WebDriver driver;

    @Before
    public void setup() {
        String browser = System.getProperty("browser", "chrome"); // default: chrome
        driver = DriverFactory.initDriver(browser);
        logger.info("Browser launched: {}", browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            try {
                // Attach screenshot to Cucumber report
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Save screenshot locally
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File dest = new File("screenshots/" + scenario.getName().replaceAll(" ", "_") + ".png");
                dest.getParentFile().mkdirs();
                Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                logger.info("Screenshot saved at: {}", dest.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Error saving screenshot: {}", e.getMessage());
            } finally {
                driver.quit();
                logger.info("Browser closed.");
            }
        } else {
            logger.warn("Driver was null, skipping screenshot & quit.");
        }
    }
}
