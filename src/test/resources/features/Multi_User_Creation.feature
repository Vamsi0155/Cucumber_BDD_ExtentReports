
Feature: Let's create multiple users

  Background: let's login into the application as Admin before every scenario
    Given launch the browser
    And "Admin" login into the Application

  Scenario Outline: Create a new user for Orange HRM
    Given click on the "Admin" of Menu Bar
    And let's search for user if already existing?
      | User Name     |
      | <User Name>   |
    When created a new user with below details:
      | User Role   | Employee Name | Status   | User Name   | Password   |
      | <User Role> | Orange        | <Status> | <User Name> | Password#  |
    Then verify below message
      | Message            |
      | Successfully Saved |
    And logout from the Application

    Examples:
      | User Name | User Role | Status  |
      | Test0010  | ESS       | Enabled |
      | Test0011  | ESS       | Enabled |
