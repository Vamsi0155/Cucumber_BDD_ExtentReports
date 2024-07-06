
Feature: Verify the user creation, modification and deletion end to end

  Background: let's login into the application as Admin before every scenario
    Given launch the browser
    And "Admin" login into the Application

  Scenario: Create a new user for Orange HRM
    Given click on the "Admin" of Menu Bar
    And let's search for user if already existing?
    | User Name |
    | Test011    |
    When created a new user with below details:
    | User Role | Employee Name | Status  | User Name | Password   |
    | ESS       | Orange        | Enabled | Test011   | Password#  |
    Then verify below message
    | Message            |
    | Successfully Saved |
    And logout from the Application


  Scenario: A view the newly created user
    Given click on the "Admin" of Menu Bar
    When fetch the user details from the list
    Then verify the new user below details:
      | User Role | Employee Name | Status  |
      | ESS       | Orange Test   | Enabled |
    And logout from the Application


  Scenario: Modify the newly created user
    Given click on the "Admin" of Menu Bar
    And search for the user
    When click on the "Edit" option
    And Modify the user with below details:
    | Status   |
    | Disabled |
    Then verify below message
      | Message              |
      | Successfully Updated |
    And logout from the Application


  Scenario: Delete the newly create user
    Given click on the "Admin" of Menu Bar
    And search for the user
    When click on the "Delete" option
    And Confirm with "Yes, Delete" option
    Then verify below message
      | Message              |
      | Successfully Deleted |
    And logout from the Application