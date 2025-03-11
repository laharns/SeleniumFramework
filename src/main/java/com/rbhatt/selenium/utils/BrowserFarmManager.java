package com.rbhatt.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BrowserFarmManager {

    private PropertyFileReader propertyFileReader;

    public BrowserFarmManager(PropertyFileReader propertyFileReader) {
        this.propertyFileReader = propertyFileReader;
    }

    public WebDriver getRemoteWebDriver() throws MalformedURLException {
        String browserFarm = propertyFileReader.getProperty("browserFarm");
        String username = propertyFileReader.getProperty("browserFarmUsername");
        String accessKey = propertyFileReader.getProperty("browserFarmAccessKey");
        String browser = propertyFileReader.getProperty("browserFarmBrowser");
        String browserVersion = propertyFileReader.getProperty("browserFarmBrowserVersion");
        String os = propertyFileReader.getProperty("os");
        String osVersion = propertyFileReader.getProperty("osVersion");
        boolean videoRecording = Boolean.parseBoolean(propertyFileReader.getProperty("videoRecording"));
        boolean networkLogs = Boolean.parseBoolean(propertyFileReader.getProperty("networkLogs"));
        String resolution = propertyFileReader.getProperty("resolution");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", browserVersion);

        // Add additional capabilities dynamically based on the browser farm
        switch (browserFarm.toLowerCase()) {
            case "browserstack":
                Map<String, Object> bstackOptions = Map.of(
                        "os", os,
                        "osVersion", osVersion,
                        "resolution", resolution,
                        "userName", username,
                        "accessKey", accessKey
                );
                if (videoRecording) {
                    bstackOptions.put("debug", true); // Enables debugging and video recording
                }
                if (networkLogs) {
                    bstackOptions.put("networkLogs", true); // Captures network logs
                }
                capabilities.setCapability("bstack:options", bstackOptions);
                break;

            case "saucelabs":
                if (videoRecording) {
                    capabilities.setCapability("sauce:options", Map.of("recordVideo", true));
                }
                if (networkLogs) {
                    capabilities.setCapability("sauce:options", Map.of("recordLogs", true));
                }
                if (resolution != null && !resolution.isEmpty()) {
                    capabilities.setCapability("screenResolution", resolution);
                }
                capabilities.setCapability("os", os);
                capabilities.setCapability("os_version", osVersion);
                break;

            case "lambdatest":
                if (videoRecording) {
                    capabilities.setCapability("video", true);
                }
                if (networkLogs) {
                    capabilities.setCapability("network", true);
                }
                if (resolution != null && !resolution.isEmpty()) {
                    capabilities.setCapability("resolution", resolution);
                }
                capabilities.setCapability("os", os);
                capabilities.setCapability("os_version", osVersion);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser farm: " + browserFarm);
        }


        // Build the remote URL dynamically based on browser farm
        String remoteUrl = null;
        switch (browserFarm.toLowerCase()) {
            case "browserstack":
                remoteUrl = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
                break;
            case "saucelabs":
                remoteUrl = "https://" + username + ":" + accessKey + "@ondemand.saucelabs.com/wd/hub";
                break;
            case "lambdatest":
                remoteUrl = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser farm: " + browserFarm);
        }

        return new RemoteWebDriver(new URL(remoteUrl), capabilities);
    }
}
