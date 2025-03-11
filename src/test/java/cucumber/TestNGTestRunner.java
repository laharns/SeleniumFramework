package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;


@Test
@CucumberOptions(
		features = "src/test/java/cucumber",
		glue = {"com.rbhatt.selenium.StepDefinitions","cucumber"},
		monochrome = true,
		//tags = "@AllTest",//@Regression",
		publish = true,
		plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests {
}
