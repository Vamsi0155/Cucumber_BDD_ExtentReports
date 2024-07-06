
Feature: Let's create a new user and sign-in with that user

  Scenario: create an user
    Given launch the browser
    And "Admin" login into the Application
    And click on the "Admin" of Menu Bar
    And let's search for user if already existing?
      | User Name |
      | Test009    |
    When created a new user with below details:
      | User Role | Employee Name | Status  | User Name | Password   |
      | ESS       | Orange        | Enabled | Test009   | Password#  |
    Then verify below message
      | Message            |
      | Successfully Saved |
    And logout from the Application


  Scenario: sing-in with a newly create user
    Given launch the browser
    When "Test009" login into the Application
    Then verify the new user
    Then logout from the Application