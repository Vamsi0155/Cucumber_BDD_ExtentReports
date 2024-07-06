package com.Orange.StepDefinitions;

import com.Orange.Utilities.CustomListener;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileNotFoundException;

public class Hooks {

    /*
     * We can create many Before & After's with help order. 0 is the default value.
     * Add conditions like "@After("@browser and not @headless")".
     * Pass "Scenario" as parameter to any tag.
     *
     * BeforeStep hook is executed the AfterStep hooks will also be executed regardless of the result of the step.
     * If a step did not pass, the following step and its hooks will be skipped.
     */


    public static String featureName;

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @BeforeStep
    public void doSomethingBeforeStep(Scenario scenario){

    }

    @AfterStep
    public void doSomethingAfterStep(Scenario scenario){

    }

    @Before
    public void beforeScenario(Scenario scenario) {

        logger.info("Entered before scenario");

        featureName = null;
        featureName = new File(scenario.getUri()).getName();
        featureName = featureName.substring(0, featureName.lastIndexOf(".feature"));

        String featurePath = scenario.getUri().getPath();
        CustomListener.getTestStartedLog(scenario.getName(), featurePath.substring(featurePath.indexOf("src")));

        System.out.println("********** " + scenario.getName() + " is started *********");
    }


    @After
    public void afterScenario(Scenario scenario) {

        System.out.println("********** " + scenario.getName() + " is completed *********");

        CustomListener.getTestStatusLog(scenario.getName(), scenario.getStatus().toString());
        logger.info("Entered after scenario");

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            System.out.println("Screenshot is attached to the report");
            logger.info("Screenshot is attached to the report");
        }
        BaseClass.closeBrowser();
        logger.info("Browser is closed");
    }

    @BeforeAll
    public static void beforeAll() throws FileNotFoundException {
        // Runs before all scenarios

        logger.info("******************* Test Suit Started **********************");
        System.out.println("******************* Run Started **********************");
    }

    @AfterAll
    public static void afterAll() {
        // Runs after all scenarios

        System.out.println("******************* Run Completed **********************");
        logger.info("******************* Test Suit Completed **********************");
    }

}
