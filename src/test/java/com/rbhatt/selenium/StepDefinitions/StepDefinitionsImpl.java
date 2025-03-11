package com.rbhatt.selenium.StepDefinitions;

import com.rbhatt.selenium.PageObjects.*;
import com.rbhatt.selenium.Base.BaseTest;
import com.rbhatt.selenium.utils.PropertyFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class StepDefinitionsImpl extends BaseTest {
	
	private final PropertyFileReader propertyFileReader;
	public LandingPage landingPage;
	public ProductCatalouge productCatalouge;
	public CartPage cartPage;
	public PlaceOrder placeOrder;
	public OrdersHistory ordersHistory;
	String orderID;
	
	public StepDefinitionsImpl() throws IOException {
		propertyFileReader = new PropertyFileReader(System.getProperty("user.dir")+"//src//test//resources//testdata.properties");
	}
	
	@Given("I landed on the Ecommerce Page")
	@Step("Navigate to the Ecommerce Login Page")
	public void I_landed_on_the_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^I Logged in with email (.+) and password (.+)$")
	@Step("Login with valid credentials")
	public void I_Logged_in_with_email_and_password(String emailKey, String passwordKey){
		String email = propertyFileReader.getProperty(emailKey);
		String password = propertyFileReader.getProperty(passwordKey);
		productCatalouge = landingPage.loginAction(email, password);
	}
	
	@When("^I add product (.+) to the Cart$")
	@Step("Add Product to the Cart")
	public void I_add_product_to_the_Cart(String productKey){
		String product = propertyFileReader.getProperty(productKey);
		List<WebElement> productNames = productCatalouge.getProductList();
		WebElement prod = productCatalouge.getProductByName(product);
		productCatalouge.addToCart(prod);
	}
	
	@And("^Checkout (.+) and submit the Order$")
	@Step("Checkout Product & Submit Order")
	public void Checkout_Product_and_submit_the_Order(String productKey){
		String product = propertyFileReader.getProperty(productKey);
		cartPage = productCatalouge.goToCart();
		Boolean isAvailable = cartPage.isProductAvailable(product);
		Assert.assertTrue(isAvailable);
		placeOrder = cartPage.checkout();
		placeOrder.placeOrder();
	}
	
	@Then("^(.+) message is displayed on Confirmation Page$")
	@Step("Validate the Confirmation Message")
	public void Confirmation_message_is_displayed_on_Confirmation_Page(String confirmationMessageKey){
		String confirmationMessage = propertyFileReader.getProperty(confirmationMessageKey);
		String confirmMessage = placeOrder.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.contains(confirmationMessage));
		orderID = placeOrder.getOrderID();
	}
	
	@And("I should see the Order on Order History Page")
	@Step("Verify Order on the Order History Page")
	public void I_should_see_the_Order_on_Order_History_Page(){
		ordersHistory = productCatalouge.goToOrderHistory();
		Boolean isOrderAvailable = ordersHistory.isOrderAvailable(orderID);
		Assert.assertTrue(isOrderAvailable);
	}
	
	@Then("^(.+) message is displayed$")
	@Step("Verify Login Error message is displayed")
	public void Login_error_message_is_displayed(String errorMessageKey){
		String errorMessage = propertyFileReader.getProperty(errorMessageKey);
		Assert.assertEquals(errorMessage,landingPage.getLoginErrorMessage());
	}
}
