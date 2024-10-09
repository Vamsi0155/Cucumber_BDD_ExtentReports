# Cucumber BDD framework with Extent reports

## Overview
This repository contains a Cucumber BDD framework integrated with Extent Reports for testing web applications. The framework follows behavior-driven development principles, providing a clear and structured approach to writing test scenarios. OrangeHRM, a practice web application, is used as the subject of UI automation testing.
### Key Features
#### Cucumber BDD:
- Enables writing human-readable test scenarios that bridge the gap between non-technical stakeholders and developers.
#### Extent Reports:
- Generates visually appealing and detailed test reports with screenshots, logs, and results.
#### Page Object Model (POM):
- Helps structure test code for maintainability and reusability.
#### Data-Driven Testing:
- Supports parameterized tests using external data sources for greater test coverage.
#### Cross-Browser Support:
- Tests can be executed on different browsers like Chrome, Firefox, etc.

## Prerequisites
- Java 17 or higher
- Maven 3.6v or higher
- Junit 5.0v or higher
- Selenium 4.20v or higher
- Cucumber 7.16v or higher
- Extent-reports 5.0v or higher

## How to run form cmd line
In the pom.xml, we have configured Extent reports and run the features by following different commands.

### To build the Project
To build the project and download all dependencies, run the whole suite following Maven command:
```bash
mvn clean install
```

### To run specific feature, use the following command:
```bash
mvn clean install -Dcucumber.options="--features src/test/resources/Admin_Module/UserOperation.feature"
```

### To run multiple features, use the following command:
```bash
mvn clean install -Dcucumber.options="--features src/test/resources/Admin_Module/UserOperation.feature,src/test/resources/Admin_Module/Multi_User_Creation.feature"
```

### To run features with Tag's, use the following command:
```bash
mvn clean install -Dcucumber.options="--tags @Regression"
```

### To run features with Tag's, use the following command:
```bash
mvn clean install -Dcucumber.options="--features src/test/resources/Admin_Module/UserOperation.feature --tags @Regression"
```

## Jenkins CI/CD

#### General Section:
 Select "This project is parameterized" and set below parameters.
1. For 1st parameter, select String parameter and set below details:
 - Name: Features
 - Default value: src/test/resources/features
 - Description: -- Pass the features path here by separated coma (,). -- By default, it will pick up all features.
2. For 2nd parameter, select choice parameter and set below details:
 - Name: Tags
 - Choices: Regression, Smoke, Sanity
 - Description: -- Choose the tags. By default Regression
#### Build Section:
1. For Root POM, give as "pom.xml"
2. For Goals and Options, use the following command:
```bash
clean verify -Dcucumber.options="--features ${Features} --tags @${Tags}"
```
3. Go to "Advanced" and check the "use custom workspace". Add project directory path.

Note: Add project path in the custom workspace field and Also remainings as per requirements.
