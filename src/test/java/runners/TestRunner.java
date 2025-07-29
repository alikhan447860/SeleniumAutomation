package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-reports",
                "junit:target/cucumber-junit.xml"
        },
        monochrome = true,
        publish = false // Optional: set to true if you want to publish to Cucumber Reports online
)
public class TestRunner {
}