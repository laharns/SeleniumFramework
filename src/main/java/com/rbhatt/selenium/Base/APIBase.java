package com.rbhatt.selenium.Base;

import com.rbhatt.selenium.utils.PropertyFileReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

/**
 * APIBase class provides a foundational setup for all API test cases.
 * It initializes RestAssured configurations such as base URI and common request specifications.
 */
public abstract class APIBase {

    protected RequestSpecification request; // Common request specification for all API tests
    protected PropertyFileReader propertyFileReader;

    public APIBase() throws IOException {

        // Load properties using PropertyFileReade
        propertyFileReader = new PropertyFileReader("src/main/resources/GlobalData.properties");

        // Set the base URI for API requests
        RestAssured.baseURI = propertyFileReader.getProperty("BaseURI"); // Replace with your actual API base URL

        // Initialize the request specification with default headers
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
}
