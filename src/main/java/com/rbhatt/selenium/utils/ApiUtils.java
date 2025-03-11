package com.rbhatt.selenium.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * ApiUtils provides reusable methods for common API operations and validations.
 */
public class ApiUtils {

    /**
     * Performs a GET request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @return The Response object.
     */
    public static Response sendGetRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Performs a POST request with a payload to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param payload  The JSON payload as a String.
     * @return The Response object.
     */
    public static Response sendPostRequest(String endpoint, String payload) {
        return given()
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Performs a PUT request with a payload to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param payload  The JSON payload as a String.
     * @return The Response object.
     */
    public static Response sendPutRequest(String endpoint, String payload) {
        return given()
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Performs a PUT request with a payload to the specified endpoint + queryString.
     *
     * @param endpoint The API endpoint.
     * @param queryString The QueryString parameter to pass in the URL
     * @param payload  The JSON payload as a String.
     * @return The Response object.
     */
    public static Response sendPutRequest(String endpoint, String payload, String queryString) {
        return given()
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Performs a DELETE request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @return The Response object.
     */
    public static Response sendDeleteRequest(String endpoint) {
        return given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Validates the status code of a Response.
     *
     * @param response         The Response object.
     * @param expectedStatusCode The expected status code.
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            throw new AssertionError("Expected status code: " + expectedStatusCode +
                    ", but got: " + response.getStatusCode());
        }
    }
}
