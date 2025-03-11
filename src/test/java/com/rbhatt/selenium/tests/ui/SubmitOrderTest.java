package com.rbhatt.selenium.tests.ui;

import com.rbhatt.selenium.PageObjects.*;
import com.rbhatt.selenium.Base.BaseTest;
import com.rbhatt.selenium.TestComponents.Retry;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
    
    String orderID;
    
    @Test(dataProvider = "getData",groups = {"PurchaseOrder"},retryAnalyzer = Retry.class)
    public void submitOrder(HashMap<String,String> input) throws IOException {
        
        //1. Perform Login
        ProductCatalouge productCatalouge = landingPage.loginAction(input.get("email"), input.get("password"));
        
        //2. Get all Product Names
        List<WebElement> productNames = productCatalouge.getProductList();
        WebElement prod = productCatalouge.getProductByName(input.get("product"));
        productCatalouge.addToCart(prod);
        
        //3. Apply explicit wait for the Toast visibility & click on Cart Link from header
        CartPage cartPage = productCatalouge.goToCart();
        
        //4. Check the Added Product is there in the Cart listing page
        Boolean isAvailable = cartPage.isProductAvailable(input.get("product"));
        Assert.assertTrue(isAvailable);
        PlaceOrder placeOrder = cartPage.checkout();
        
        //5. Place Order
        placeOrder.placeOrder();
        
        //6. Assert Confirmation Message
        String confirmMessage = placeOrder.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.contains("THANKYOU"));
        
        //7. Get Order ID
        orderID = placeOrder.getOrderID();
        System.out.println(input.get("email")+" "+orderID);
    }
    
    @Test(dataProvider = "getData",dependsOnMethods = "submitOrder", retryAnalyzer = Retry.class)
    public void orderHistoryPage(HashMap<String,String> input) throws IOException {
        //1. Perform Login
        ProductCatalouge productCatalouge = landingPage.loginAction(input.get("email"), input.get("password"));
        // Go to Order History Page
        OrdersHistory ordersHistory = productCatalouge.goToOrderHistory();
        Boolean isOrderAvailable = ordersHistory.isOrderAvailable(orderID);
        System.out.println(input.get("email")+" "+orderID+" "+isOrderAvailable);
        Assert.assertTrue(isOrderAvailable);
    }
    
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//com//rbhatt//selenium//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }
}
