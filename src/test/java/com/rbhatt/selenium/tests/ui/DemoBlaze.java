package com.rbhatt.selenium.tests.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DemoBlaze {
	public static void main(String[] args) {
		String myProduct = "ASUS Full HD";
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.demoblaze.com/");
		boolean productFound = false;
		while(true){
			//List<WebElement> products = driver.findElements(By.xpath("//div[@class='card-block']/h4/a"));
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='card-block']/h4/a")));
			for (WebElement product : products) {
				System.out.println(product.getText());
				if (product.getText().equalsIgnoreCase(myProduct)) {
					productFound = true;
					product.click();
					break;
				}
			}
			if(productFound){
				System.out.println("Product "+ myProduct + " Found");
				break;
			}else {
				WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("next2")));
				nextButton.click();
				wait.until(ExpectedConditions.stalenessOf(products.get(0)));
			}
		}
		driver.quit();
	}
}
