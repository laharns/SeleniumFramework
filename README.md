# Selenium Framework

This Selenium Framework is designed for efficient and scalable web application testing, integrating key features like video recording, retry logic, and headless browser support.

## Web Automation Features

- **Cross-Browser Testing**: Supports multiple browsers to ensure comprehensive test coverage.
- **Parallel Execution**: Facilitates concurrent test runs to reduce execution time.
- **Video Recording**: Captures test executions, configurable to record all tests or only on failures.
- **Retry Logic**: Automatically retries failed tests to handle transient issues.
- **Headless Mode Support**: Allows tests to run without a GUI, suitable for CI/CD pipelines.
- **Allure Reporting**: Generates detailed and interactive test reports.

## API Testing Features (Rest Assured)

- Dynamic request creation(GET, POST, PUT, DELETE).
- Assertion of response codes, body and headers.
- Easy integration with the framework's reporting and retry logic.

## Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher.
- **Maven**: For dependency management and build automation.
- **Browser Drivers**: Ensure the appropriate drivers (e.g., ChromeDriver, GeckoDriver) are available in your system's PATH.
- **Rest Assured Dependencies**: Included in the Maven pom.xml.

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/RishilB/SeleniumFramework.git

2. **Navigate to the Project Directory**:
   ```bash
    cd SeleniumFramework

3. **Install Dependencies:**:
   ```bash
   mvn clean install

## Configuration

The framework uses a GlobalData.properties file located in src/main/resources/ for configuration settings:

**Web Testing Configuration**:

**recordVideo**: Controls video recording behavior. Options:
- **On**: Records all test scenarios.
- **OnFailure**: Records only when a test scenario fails.
- **Off**: Disables video recording.

**headless**: Runs tests in headless mode when set to true.

**API Testing Configuration**

- **BaseURI**: The base URL for API testing (e.g., https://reqres.in).
- Headers like Content-Type and Accept can be customized programmatically.

Ensure the GlobalData.properties file reflects your desired settings.

## Running Tests

**Web Tests**:

1. Through TestNG XML:

Locate the desired TestNG XML file in the testSuites directory.
Right-click the XML file and select Run.

2. Through Maven
    ```bash
    mvn test -DsuiteXmlFile=testSuites/YourTestSuite.xml

3. Through Maven Profile
    ```bash
   mvn clean install -P<YourProfileName>

**API Tests**:

1. Run Specific API Tests
   ```bash
   mvn clean test -Dtest=APITests
   
2. Run Using a Maven Profile
   ```bash
   mvn clean install -Papi-tests

## Video Recording
The framework supports configurable video recording of test executions:

- **Configuration**: Set the recordVideo property in GlobalData.properties to On, OnFailure, or Off.
- **Storage**: Videos are saved in the videos directory, with filenames corresponding to the test scenario names and timestamps.
- **Headless Mode**: Video recording is disabled in headless mode to maintain compatibility.

## Retry Logic
Failed tests are retried based on the configuration:

- **Implementation**: The Retry class manages retry attempts, configurable via TestNG listeners.
- **Configuration**: Set the maximum retry attempts in the Retry class.

## Reporting
The framework integrates with Allure to generate comprehensive test reports:

- **Setup**: Ensure Allure is installed on your system.
- **Generate Report**:
  ```bash
  allure serve allure-results
- **Access Report**: The report will be available at http://localhost:portNumber in your browser.

## Acknowledgements
Special thanks to the contributors and the open-source community for their invaluable support.