package com.rbhatt.selenium.PageObjects;

import com.rbhatt.selenium.AbstractCompoments.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {
	
	@FindBy(xpath = "//div[@class='cartSection']/h3")
	List<WebElement> cartProductsElements;

	@FindBy(css = "li[class='totalRow'] button[type='button']")
	WebElement checkoutBtn;

	WebDriver driver;
	public CartPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	
	public Boolean isProductAvailable(String productName){
        return cartProductsElements.stream().anyMatch(product -> product.getText().contains(productName));
	}

	public PlaceOrder checkout(){
		scrollToElement(checkoutBtn);
		click(checkoutBtn);
		return new PlaceOrder(driver);
	}
}
