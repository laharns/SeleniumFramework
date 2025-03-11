package com.rbhatt.selenium.PageObjects;

import com.rbhatt.selenium.AbstractCompoments.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Stream;

public class OrdersHistory extends AbstractComponent {
	
	@FindBy(xpath = "//tbody/tr/th")
	List<WebElement> allOrdersEle;
	
	WebDriver driver;
	public OrdersHistory(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isOrderAvailable(String orderID){
		return allOrdersEle.stream().anyMatch(myOrder -> myOrder.getText().equalsIgnoreCase(orderID));
	}
}
