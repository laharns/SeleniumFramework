package com.rbhatt.selenium.PageObjects;

import com.rbhatt.selenium.AbstractCompoments.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalouge extends AbstractComponent {
	
	By productSections = By.cssSelector(".mb-3");
	By addToCartBtn = By.xpath("button[contains(.,'Add To Cart')]");
	By toastContainer = By.cssSelector("#toast-container");
	
	@FindBy(className="card-body")
	List<WebElement> productNames;

	
	WebDriver driver;
	public ProductCatalouge(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getProductList(){
		waitForElementToAppear(productSections);
		return productNames;
	}
	
	public WebElement getProductByName(String productName){
		return productNames.stream().filter(product ->
				product.getText().contains(productName)).findAny().orElse(null);
	}
	
	public void addToCart(WebElement prod){
		prod.findElement(addToCartBtn).click();
		waitForElementToAppear(toastContainer);
		waitForElementToDisappear(toastContainer);
	}
	
}
