package com.rbhatt.selenium.tests.api;

import com.rbhatt.selenium.Base.APIBase;
import com.rbhatt.selenium.utils.ApiUtils;
import com.rbhatt.selenium.utils.JsonDataReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertNotNull;

/**
 * Example API tests using ApiUtils.
 */
public class APITests extends APIBase {

    private List<HashMap<String, String>> testData;

    public APITests() throws IOException {
        super(); // Explicitly call the parent constructor
    }

    @BeforeClass
    public void setup() throws IOException {
        // Load test data from APITestData.json
        JsonDataReader jsonDataReader = new JsonDataReader();
        testData = jsonDataReader.readData("src/test/resources/APITestData.json");
    }

    /**
     * Helper method to get test data by key.
     */
    private HashMap<String, String> getDataForKey(String key) {
        Optional<HashMap<String, String>> data = testData.stream()
                .filter(map -> map.get("key").equals(key))
                .findFirst();
        return data.orElseThrow(() -> new RuntimeException("Test data not found for key: " + key));
    }

    @Test
    public void testCreateUser() {
        // Get data for "createUser" key
        HashMap<String, String> data = getDataForKey("createUser");

        String endpoint = data.get("endPoint");
        String payload = String.format("{ \"name\": \"%s\", \"job\": \"%s\" }", data.get("name"), data.get("job"));

        // Send POST request
        Response response = ApiUtils.sendPostRequest(endpoint, payload);

        // Validate status code
        ApiUtils.validateStatusCode(response, 400);
        assertNotNull(response.jsonPath().getString("id"));
        System.out.println("User ID is: " + response.jsonPath().getString("id"));
    }

    @Test
    public void testUpdateUser() {
        // Get data for "updateUser" key
        HashMap<String, String> data = getDataForKey("updateUser");

        String endpoint = data.get("endPoint");
        String payload = String.format("{ \"name\": \"%s\", \"job\": \"%s\" }", data.get("name"), data.get("job"));

        // Send PUT request
        Response response = ApiUtils.sendPutRequest(endpoint, payload);

        // Validate status code
        ApiUtils.validateStatusCode(response, 200);
        assertNotNull(response.jsonPath().getString("updatedAt"));
    }
}