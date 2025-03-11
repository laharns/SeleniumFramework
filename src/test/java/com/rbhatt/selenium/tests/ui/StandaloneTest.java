package com.rbhatt.selenium.tests.ui;

import com.rbhatt.selenium.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandaloneTest {
    public static void main(String[] args){

        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // 1. Perform Login
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage landingPage = new LandingPage(driver);
        driver.findElement(By.id("userEmail")).sendKeys("risshilbhatt@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Test@123");
        driver.findElement(By.id("login")).click();

        //2. Get all Product Names
        List<WebElement> productNames = driver.findElements(By.className("card-body"));
        WebElement prod = productNames.stream().filter(product ->
                product.getText().contains(productName)).findAny().orElse(null);
        prod.findElement(By.xpath("button[contains(.,'Add To Cart')]")).click();

        //3. Apply explicit wait for the Toast visibility & click on Cart Link from header
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //4. Check the Added Product is there in the Cart listing page
        List<WebElement> cartProductsElements = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
        boolean isAvailable = cartProductsElements.stream().anyMatch(product -> product.getText().contains(productName));
        Assert.assertTrue(isAvailable);
        driver.findElement(By.cssSelector("li[class='totalRow'] button[type='button']")).click();
        
        //5. Place Order
        driver.findElement(By.xpath("//div/input[@placeholder='Select Country']")).sendKeys("Ind");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='form-group']/section"))));
        List<WebElement> countries = driver.findElements(By.xpath("//div[@class='form-group']/section/button"));
        for(WebElement country : countries){
            if(country.getText().equalsIgnoreCase("Indonesia")){
                country.click();
                break;
            }
        }
        driver.findElement(By.xpath("//div[@class='actions']/a")).click();
        
        //6. Assert Confirmation Message
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        System.out.println(confirmMessage);
        Assert.assertTrue(confirmMessage.contains("THANKYOU"));
        driver.quit();
    }
}
