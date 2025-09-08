package saucedemo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",    // path to .feature files
    glue = {"saucedemo.stepDefinitions"},        // package with step defs + hooks
    plugin = {
        "pretty",
        "html:reports/cucumberReport.html",
        "json:reports/cucumber.json"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
