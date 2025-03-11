package com.rbhatt.selenium.PageObjects;

import com.rbhatt.selenium.AbstractCompoments.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PlaceOrder extends AbstractComponent {

    By countriesEle = By.xpath("//div[@class='form-group']/section");

    @FindBy(xpath = "//div/input[@placeholder='Select Country']")
    WebElement country;

    @FindBy(xpath = "//div[@class='form-group']/section/button")
    List<WebElement> countries;

    @FindBy(css = ".action__submit")
    WebElement placeOrderButton;

    @FindBy(css = ".hero-primary")
    WebElement confirmationMessageEle;
    
    @FindBy(xpath = "//label[@class='ng-star-inserted']")
    WebElement orderIDEle;

    WebDriver driver;
    public PlaceOrder(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void placeOrder(){
        sendKeys(country,"Indo");
        waitForElementToAppear(countriesEle);
        for(WebElement country : countries){
            if(country.getText().equalsIgnoreCase("Indonesia")){
                click(country);
                break;
            }
        }
        scrollToElement(placeOrderButton);
        waitForWebElementToVisible(placeOrderButton);
        click(placeOrderButton);
    }

    public String getConfirmationMessage(){
        return confirmationMessageEle.getText();
    }
    
    public String getOrderID(){
        // Get the Order ID from Thank You Page.
	    return orderIDEle.getText().trim().replace("|","").trim();
    }
}
