package com.Orange.CucumberOptions;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.Orange.StepDefinitions"},
        monochrome = true,
        plugin = {
                "pretty",
                "com.Orange.Utilities.CustomListener",
                "html:reports/CucumberReports/Report.html",
                "rerun:target/failedScenariosPath.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class CucumberTestRunner {

}
