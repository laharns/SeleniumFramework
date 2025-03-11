package com.rbhatt.selenium.Base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbhatt.selenium.PageObjects.LandingPage;
import com.rbhatt.selenium.utils.BrowserFarmManager;
import com.rbhatt.selenium.utils.PropertyFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public abstract class BaseTest {

    public WebDriver driver;
	public LandingPage landingPage;
    protected PropertyFileReader propertyFileReader;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	
	public WebDriver initializeDriver() throws IOException {

		// Load properties using PropertyFileReade
		propertyFileReader = new PropertyFileReader("src/main/resources/GlobalData.properties");

		// Determine where to run the tests
		String runOn = propertyFileReader.getProperty("runOn");

		if(runOn.equalsIgnoreCase("local")){
			String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : propertyFileReader.getProperty("browser");
			boolean isHeadless = Boolean.parseBoolean(propertyFileReader.getProperty("headless"));
			boolean maximizeWindow = Boolean.parseBoolean(propertyFileReader.getProperty("maximizeWindow"));
			int implicitWait = Integer.parseInt(propertyFileReader.getProperty("implicitWait"));
			if(browserName.contains("chrome")){
				ChromeOptions options = new ChromeOptions();
				WebDriverManager.chromedriver().setup();
				if(isHeadless){
					options.addArguments("headless");
					options.addArguments("--disable-gpu");
				}
				driver = new ChromeDriver(options);
				driver.manage().window().setSize(new Dimension(1440,900));
			}else if(browserName.equalsIgnoreCase("firefox")){
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}else if(browserName.equalsIgnoreCase("edge")){
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
			if (maximizeWindow && !isHeadless) {
				driver.manage().window().maximize();
			}
		}else if(runOn.equalsIgnoreCase("browserfarm")){
			BrowserFarmManager browserFarmManager = new BrowserFarmManager(propertyFileReader);
			driver = browserFarmManager.getRemoteWebDriver();
		}else{
			throw new RuntimeException("Unsupported runOn value:"+ runOn);
		}
		tdriver.set(driver);
		return getDriver();
	}

	public static synchronized WebDriver getDriver(){
		return tdriver.get();
	}

    public List<HashMap<String, String>> getJsonDataToMap(String fileName) throws IOException {
		
		// Read the Json File and convert to String
		String jsonContent = FileUtils.readFileToString(new File(fileName),
				StandardCharsets.UTF_8);
		
		// Convert String to HashMap
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod
	public void tearDown(){
		if (driver != null) {
			System.out.println("Not Null");
			driver.close();
		}else{
			System.out.println("Null");
		}
	}

}
