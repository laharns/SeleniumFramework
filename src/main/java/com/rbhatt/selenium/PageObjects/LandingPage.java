package com.rbhatt.selenium.PageObjects;

import com.rbhatt.selenium.AbstractCompoments.AbstractComponent;
import com.rbhatt.selenium.utils.PropertyFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import java.io.IOException;

public class LandingPage extends AbstractComponent {

	private final String baseUrl;
	PropertyFileReader propertyFileReader;
	
	@FindBy(id="userEmail")
	WebElement userEmailEle;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement loginBtnEle;
	
	@FindBy(css="div[aria-label*='Incorrect']")
	WebElement errorMessageEle;
	
	WebDriver driver;
	public LandingPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		try {
			propertyFileReader = new PropertyFileReader("src/main/resources/GlobalData.properties");
			this.baseUrl = propertyFileReader.getProperty("baseUrl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to load base URL from GlobalData.properties", e);
		}
    }
	
	public void goTo(){
		driver.get(baseUrl);
		waitForPageLoadComplete(10);
	}

	public ProductCatalouge loginAction(String email, String password){
		sendKeys(userEmailEle,email);
		sendKeys(passwordEle, password);
		click(loginBtnEle);
		return new ProductCatalouge(driver);
	}
	
	public String getLoginErrorMessage(){
		waitForWebElementToVisible(errorMessageEle);
		return errorMessageEle.getText();
	}
}
