package com.Orange.Utilities;

import com.Orange.Factory.Loader;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomListener implements EventListener {

    private static final Logger logger = LogManager.getLogger(CustomListener.class);

    private static String startTime;
    private static String failedStepLine;
    private static int failedStepNum;



    @Override
    public void setEventPublisher(EventPublisher publisher) {

        //publisher.registerHandlerFor(TestCase.class, this::onFeatureStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::onScenarioStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::onStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onScenarioFinished);
    }

    public void onFeatureStarted(TestCase feature) {

        Loader.clearGlobalValues();
        logger.info("Global values are cleared");
    }

    public void onScenarioStarted(TestCaseStarted test) {

        startTime = null;
        test.getInstant();
        Instant.now().atZone(ZoneId.systemDefault());
        startTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX'['VV']'"));
    }

    public void onStepStarted(TestStepStarted step) {

    }

    public void onStepFinished(TestStepFinished step) {

        if(!step.getResult().getStatus().isOk()) {
            failedStepLine = null;
            PickleStepTestStep pickle = (PickleStepTestStep)step.getTestStep();
            Step st = pickle.getStep();
            failedStepLine = st.getText();
            failedStepNum = st.getLine();
            System.out.println("Failed at line: " + failedStepNum + ", And Line: " + failedStepLine);
        }
    }


    public void onScenarioFinished(TestCaseFinished test) {


        this.writeRunDetailsToDB(test, test.getTestCase().getUri().getPath());
    }


    private void writeRunDetailsToDB(TestCaseFinished test, String fullPath) {

        String scenarioPath = fullPath.substring(fullPath.indexOf("features"));
        test.getInstant();
        String runDateTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("Time: " + runDateTime);
        logger.info("Test finished outcome:: Duration:" + test.getResult().getDuration().getSeconds() + ", Result:" + test.getResult().getStatus().toString() + ", Feature:" + scenarioPath + ", StartTime:" +startTime + ", TestFailedStep:" + failedStepNum + ", TestFailedStepLine:" + failedStepLine + ", FailureCause:" + test.getResult().getError());
    }


    public static void getTestStartedLog(String scenarioName, String path) {

        String testStarted =
                "\n _____ _____ ____ _____   ____ _____  _    ____ _____ _____ ____  \n" +
                        "|_   _| ____/ ___|_   _| / ___|_   _|/ \\  |  _ \\_   _| ____|  _ \\ \n" +
                        "  | | |  _| \\___ \\ | |   \\___ \\ | | / _ \\ | |_) || | |  _| | | | |\n" +
                        "  | | | |___ ___) || |    ___) || |/ ___ \\|  _ < | | | |___| |_| |\n" +
                        "  |_| |_____|____/ |_|   |____/ |_/_/   \\_\\_| \\_\\|_| |_____|____/ \n" +
                        "TEST STARTED: " + scenarioName + " \n" +
                        "----------------------------------------------------------------------("+ path + "; " + scenarioName;

        logger.info(testStarted);
    }


    public static void getTestStatusLog(String scenarioName, String status) {

        String testPassed=
                "\n        __    _____ _____ ____ _____   ____   _    ____  ____  _____ ____  \n" +
                        "  _     \\ \\  |_   _| ____/ ___|_   _| |  _ \\ / \\  / ___|/ ___|| ____|  _ \\ \n" +
                        " (_)_____| |   | | |  _| \\___ \\ | |   | |_) / _ \\ \\___ \\\\___ \\|  _| | | | |\n" +
                        "  _|_____| |   | | | |___ ___) || |   |  __/ ___ \\ ___) |___) | |___| |_| |\n" +
                        " (_)     | |   |_| |_____|____/ |_|   |_| /_/   \\_\\____/|____/|_____|____/ \n" +
                        "        /_/                                                                \n" +
                        "TEST PASSED: " + scenarioName + " \n" +
                        "----------------------------------------------------------------------";



        String testFailed =
                "\n           __  _____ _____ ____ _____   _____ _    ___ _     _____ ____  \n" +
                        "  _       / / |_   _| ____/ ___|_   _| |  ___/ \\  |_ _| |   | ____|  _ \\ \n" +
                        " (_)_____| |    | | |  _| \\___ \\ | |   | |_ / _ \\  | || |   |  _| | | | |\n" +
                        "  _|_____| |    | | | |___ ___) || |   |  _/ ___ \\ | || |___| |___| |_| |\n" +
                        " (_)     | |    |_| |_____|____/ |_|   |_|/_/   \\_\\___|_____|_____|____/ \n" +
                        "          \\_\\                                                            \n" +
                        "TEST FAILED: " + scenarioName + " \n" +
                        "----------------------------------------------------------------------";



        String testError =
                "\n         __  _____ _____ ____ _____   _____ ____  ____   ___  ____  \n" +
                        " _      / / |_   _| ____/ ___|_   _| | ____|  _ \\|  _ \\ / _ \\|  _ \\ \n" +
                        "(_)____| |    | | |  _| \\___ \\ | |   |  _| | |_) | |_) | | | | |_) |\n" +
                        " |_____| |    | | | |___ ___) || |   | |___|  _ <|  _ <| |_| |  _ < \n" +
                        "(_)    | |    |_| |_____|____/ |_|   |_____|_| \\_\\_| \\_\\\\___/|_| \\_\\\n" +
                        "        \\_\\                                                         \n" +
                        "\n" +
                        "TEST ERROR: " + scenarioName + " \n" +
                        "----------------------------------------------------------------------";

        if(status.equals("PASSED")) {
            logger.info(testPassed);
        }
        else if(status.equals("FAILED")) {
            logger.info(testFailed);
        }
        else if(status.equals("ERROR")) {
            logger.info(testError);
        }
    }


}
