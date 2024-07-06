package com.Orange.StepDefinitions;

import com.Orange.Factory.Loader;
import com.Orange.Factory.ReadConfigFiles;
import com.Orange.Utilities.CommonMethods;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.Serializers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;

import java.util.Map;

import static com.Orange.StepDefinitions.BaseClass.driver;

public class CommonSteps {

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);

    public static void loginApplication(String userName, String password) {

        if(BaseClass.element("xpath", "//h5[normalize-space()='Login']").isDisplayed()) {

            BaseClass.element("xpath", "//input[@placeholder='Username']").sendKeys(userName);
            BaseClass.enterText("xpath", "//input[@placeholder='Password']", password);
            BaseClass.clickButton("xpath", "//button[normalize-space()='Login']");
        }
        else {
            logger.info("Login is not displayed, doing refresh the page");
            BaseClass.refreshPage();

            BaseClass.element("xpath", "//input[@placeholder='Username']").sendKeys(userName);
            BaseClass.enterText("xpath", "//input[@placeholder='Password']", password);
            BaseClass.clickButton("xpath", "//button[normalize-space()='Login']");
        }
    }

    @Given("launch the browser")
    public void launch_the_browser() {

        BaseClass.loadBrowser();
        BaseClass.openUrl();
        logger.info("Browser launched successfully");
    }

    @Given("{string} login into the Application")
    public void login_into_the_application(String user) {

        String userName;
        String password;

        if(user.equalsIgnoreCase("Admin")) {

            userName = ReadConfigFiles.config.getProperty("Admin");
            password = ReadConfigFiles.config.getProperty("password");

            loginApplication(userName, password);
        }
        else {

            password = Loader.getGlobalValues().get(user);
            loginApplication(user, password);
        }

        try {
            BaseClass.wait_for_Element_until_display(10, "//h6[normalize-space()='Dashboard']");
            BaseClass.element("xpath", "//h6[normalize-space()='Dashboard']").isDisplayed();
            logger.info("logged into the application successfully");
        } catch (NoSuchElementException e) {
            logger.error("Dashboard is not displayed, ", e);
            Assertions.fail("Dashboard is not displayed");
        }
    }

    @Given("click on the {string} of Menu Bar")
    public void click_on_the_of_menu_bar(String item) {

        switch (item) {
            case "Admin" -> {
                BaseClass.clickButton("xpath", "//li[1]//a[1]//span[1]");
            }
            case "PIM" -> {
                BaseClass.clickButton("xpath", "//li[2]//a[1]//span[1]");
            }
            case "Leave" -> {
                BaseClass.clickButton("xpath", "//li[3]//a[1]//span[1]");
            }
            case "Time" -> {
                BaseClass.clickButton("xpath", "//li[4]//a[1]//span[1]");
            }
            case "Recruitment" -> {
                BaseClass.clickButton("xpath", "//li[5]//a[1]//span[1]");
            }
            case "My Info" -> {
                BaseClass.clickButton("xpath", "//li[6]//a[1]//span[1]");
            }default -> {
                // Dashboard
                BaseClass.clickButton("xpath", "//li[8]//a[1]//span[1]");
            }
        }
        logger.info("clicked on the "+item+" of Menu Bar");
    }

    @Given("let's search for user if already existing?")
    public void let_s_search_for_user_if_already_existing(DataTable table) throws InterruptedException {

        String userName = table.asMaps().getFirst().get("User Name");
        BaseClass.enterText("xpath", "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']", userName);
        BaseClass.clickButton("xpath", "//button[normalize-space()='Search']");

        String text = BaseClass.element("xpath", "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']").getText();

        Assertions.assertEquals("No Records Found", text);
        logger.info("No user was existing with {}", userName);
        Loader.setGlobalValue("User Name", userName);
    }

    @When("created a new user with below details:")
    public void created_a_new_user_with_below_details(DataTable dataTable) {

        Map<String, String> newTable = CommonMethods.parseGivenInputTable(dataTable);

        BaseClass.waitFewSeconds(2000);
        BaseClass.clickButton("xpath", "//button[normalize-space()='Add']");

        BaseClass.waitFewSeconds(2000);
        if(newTable.get("User Role").equals("Admin")) {
            BaseClass.clickButton("xpath", "//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]");
            BaseClass.clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[2]");
        }
        else {
            BaseClass.clickButton("xpath", "//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]");
            BaseClass.clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        }

        BaseClass.enterText("xpath", "//input[@placeholder='Type for hints...']", newTable.get("Employee Name"));
        BaseClass.waitFewSeconds(2000);
        BaseClass.clickButton("xpath", "//div[@class='oxd-autocomplete-wrapper']/div[2]/div[1]");

        if(newTable.get("Status").equals("Enabled")) {
            BaseClass.clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
            BaseClass.clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[2]");
        }
        else {
            BaseClass.clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
            BaseClass.clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        }

        BaseClass.enterText("xpath", "(//input[@class='oxd-input oxd-input--active'])[2]", newTable.get("User Name"));
        BaseClass.enterText("xpath", "(//input[@type='password'])[1]", newTable.get("Password"));
        BaseClass.enterText("xpath", "(//input[@type='password'])[2]", newTable.get("Password"));
        Loader.setGlobalValue(newTable.get("User Name"), newTable.get("Password"));
        System.out.println("Pass: " + newTable.get("Password"));

        BaseClass.waitFewSeconds(2000);
        BaseClass.clickButton("xpath", "//button[normalize-space()='Save']");
        logger.info("User created with below details");
    }

    @Then("verify below message")
    public void verify_below_message(DataTable dataTable) {

        BaseClass.wait_for_Element_until_display(10, "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']");
        String text = BaseClass.element("xpath", "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']").getText();
        Assertions.assertEquals(dataTable.asMaps().getFirst().get("Message"), text);
        logger.info("Below message is verified");
    }

    @Then("logout from the Application")
    public void logout_from_the_application() {

        BaseClass.waitFewSeconds(4000);
        BaseClass.clickButton("xpath", "//p[@class='oxd-userdropdown-name']");
        BaseClass.clickButton("xpath", "//a[normalize-space()='Logout']");
        logger.info("Logged out from application");
    }

    //@Given("search for the user")
    public void search_for_the_user() {


    }

    @When("click on the {string} option")
    public void click_on_the_option(String option) {

        BaseClass.waitFewSeconds(2000);
        int rowCount = BaseClass.elementList("xpath", "//div[@class='oxd-table-body']/div").size();

        if(rowCount == 1) {
            if(option.equals("Edit")) {
                BaseClass.clickButton("xpath", "//div[@class='oxd-table-card']/div/div[6]/div/button[2]");
            }
            else if(option.equals("Delete")) {
                BaseClass.clickButton("xpath", "//div[@class='oxd-table-card']/div/div[6]/div/button[1]");
            }
        }
        logger.info("clicked on the " + option + " option");
    }

    @Given("search for the user")
    @When("fetch the user details from the list")
    public void fetch_the_user_details() {

        String userName = Loader.getGlobalValues().get("User Name");
        BaseClass.enterText("xpath", "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']", userName);
        BaseClass.clickButton("xpath", "//button[normalize-space()='Search']");

        Assertions.assertTrue(BaseClass.element("xpath", "//span[normalize-space()='(1) Record Found']").isDisplayed(), "Either no user (or) more than 1 user found..");
        logger.info("User is found and fetching details...");
        BaseClass.pageScrollBottom();
        logger.info("searched/fetched user details");
    }

    @Then("verify the new user below details:")
    public void verify_user_details(DataTable table) {

        BaseClass.waitFewSeconds(2000);
        int rowCount = BaseClass.elementList("xpath", "//div[@class='oxd-table-body']/div").size();
        if(rowCount > 1) {
            System.out.println("Rows: " + rowCount);
        }
        else {
            String user = BaseClass.element("xpath", "//div[@class='oxd-table-card']/div/div[2]/div").getText();
            Assertions.assertEquals(Loader.getGlobalValues().get("User Name"), user, "Invalid user found..");

            String role = BaseClass.element("xpath", "//div[@class='oxd-table-card']/div/div[3]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("User Role"), role, "Invalid role found..");

            String empName = BaseClass.element("xpath", "//div[@class='oxd-table-card']/div/div[4]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("Employee Name"), empName, "Invalid Employee name found..");

            String status = BaseClass.element("xpath", "//div[@class='oxd-table-card']/div/div[5]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("Status"), status, "Invalid status found..");
        }
        logger.info("new user is verified with below details");
    }

    @When("Modify the user with below details:")
    public void modify_user_details(DataTable table) {

        BaseClass.wait_for_Element_until_display(10, "//button[normalize-space()='Save']");
        BaseClass.clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
        BaseClass.clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        BaseClass.waitFewSeconds(2000);
        BaseClass.clickButton("xpath", "//button[normalize-space()='Save']");
        logger.info("user modified with below details");
    }

    @When("Confirm with {string} option")
    public void confirm_with_option(String option) {

        BaseClass.waitFewSeconds(2000);
        if(option.equals("Yes, Delete")) {

            BaseClass.clickButton("xpath", "//button[normalize-space()='Yes, Delete']");
        } else if (option.equals("No, Cancel")) {

            BaseClass.clickButton("xpath", "//button[normalize-space()='No, Cancel']");
        }
        logger.info("Confirmed with " + option);
    }


    @Then("verify the new user")
    public void verifyTheNewUser() {

        BaseClass.wait_for_Element_until_display(15, "//p[@class='oxd-userdropdown-name']");
        String userName = BaseClass.element("xpath", "//p[@class='oxd-userdropdown-name']").getText();
        System.out.println("User: " + userName);
        logger.info("new user is verified");
    }
}
