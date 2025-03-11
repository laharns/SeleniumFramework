package cucumber;

import com.rbhatt.selenium.Base.BaseTest;
import com.rbhatt.selenium.utils.PropertyFileReader;
import com.rbhatt.selenium.utils.VideoRecorder;
import io.cucumber.java.After;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.logging.Logger;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.rbhatt.selenium.TestComponents.Retry;

public class Hooks extends BaseTest {
	
	private static final Logger logger = Logger.getLogger(Hooks.class.getName());
	private final VideoRecorder videoRecorder = new VideoRecorder();
	boolean isHeadless;
	String recordVideoFlag;
    {
        try {
            isHeadless = Boolean.parseBoolean(new PropertyFileReader("src/main/resources/GlobalData.properties").getProperty("headless"));
			recordVideoFlag = new PropertyFileReader("src/main/resources/GlobalData.properties").getProperty("recordVideo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll(order = 0)
	public static void cleanVideosFolder(){
		File videosDir = new File("videos");
		if (videosDir.exists() && videosDir.isDirectory()) {
			for (File file : Objects.requireNonNull(videosDir.listFiles())) {
				if (file.isFile()) {
					file.delete();
				}
			}
			logger.info("Videos folder cleaned before execution.");
		}
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		try {
			// Start recording based on recordVideo flag and headless mode
			if (!isHeadless) {
				if ("On".equalsIgnoreCase(recordVideoFlag)) {
					videoRecorder.startRecording(scenario.getName().replaceAll(" ", "_"));
					logger.info("Video recording started for scenario: " + scenario.getName());
				} else if ("onFailure".equalsIgnoreCase(recordVideoFlag) && Retry.isRetryMode()) {
					videoRecorder.startRecording(scenario.getName().replaceAll(" ", "_")+" Retried");
					logger.info("Video recording started for retry scenario: " + scenario.getName());
				}
			} else {
				logger.info("Video recording skipped due to headless mode.");
			}
		} catch (Exception e) {
			logger.severe("Failed to start video recording: " + e.getMessage());
		}
	}

	@After
	public void afterScenario(Scenario scenario) {
		WebDriver driver = BaseTest.getDriver();
		try {
			// Stop recording based on recordVideo flag and retry mode
			if (!isHeadless) {
				if ("On".equalsIgnoreCase(recordVideoFlag)) {
					videoRecorder.stopRecording();
					logger.info("Video recording stopped for scenario: " + scenario.getName());
				} else if ("onFailure".equalsIgnoreCase(recordVideoFlag) && Retry.isRetryMode()) {
					videoRecorder.stopRecording();
					logger.info("Video recording stopped for retry scenario: " + scenario.getName());
				}
			}

			// Capture and attach the screenshot to Allure report for failed scenarios
			if (scenario.isFailed()) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				Allure.addAttachment(scenario.getName(), new ByteArrayInputStream(screenshot));
			}
		} catch (Exception e){
			logger.severe("Error in afterScenario:"+ e.getMessage());
		} finally {
			if(driver != null){
				driver.quit();
			}
		}
	}
}