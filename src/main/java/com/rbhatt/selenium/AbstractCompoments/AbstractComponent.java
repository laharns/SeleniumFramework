package com.rbhatt.selenium.AbstractCompoments;

import com.rbhatt.selenium.PageObjects.CartPage;
import com.rbhatt.selenium.PageObjects.OrdersHistory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AbstractComponent{
	
	@FindBy(css = "[routerlink*='cart']")
	WebElement goToCartBtn;
	
	@FindBy(xpath = "//button[@routerlink='/dashboard/myorders']")
	WebElement goToOrdersHistoryBtn;
	
	WebDriver driver;
	public AbstractComponent(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Waits for the element located by the given locator to become visible on the page.
	 *
	 * @param findBy The By locator of the element to wait for.
	 */
	public void waitForElementToAppear(By findBy) {
		if (findBy == null) {
			throw new IllegalArgumentException("The locator cannot be null.");
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(findBy)); // Wait for visibility
		} catch (Exception e) {
			throw new RuntimeException("Failed to wait for the element to appear: " + findBy, e);
		}
	}


	/**
	 * Waits for the specified WebElement to become visible on the page.
	 *
	 * @param findBy The WebElement to wait for.
	 */
	public void waitForWebElementToVisible(WebElement findBy) {
		if (findBy == null) {
			throw new IllegalArgumentException("The WebElement cannot be null.");
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(findBy)); // Wait for visibility
		} catch (Exception e) {
			throw new RuntimeException("Failed to wait for the WebElement to become visible: " + findBy, e);
		}
	}


	/**
	 * Waits for the element located by the given locator to disappear or become invisible.
	 *
	 * @param findBy The By locator of the element to wait for.
	 */
	public void waitForElementToDisappear(By findBy) {
		if (findBy == null) {
			throw new IllegalArgumentException("The locator cannot be null.");
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy)); // Wait for invisibility
		} catch (Exception e) {
			throw new RuntimeException("Failed to wait for the element to disappear: " + findBy, e);
		}
	}

	/**
	 * Clicks on the specified WebElement after ensuring it is clickable.
	 *
	 * @param element The WebElement to be clicked.
	 */
	public void click(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("The WebElement cannot be null.");
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element)); // Ensure element is clickable
			element.click(); // Perform the click action
		} catch (Exception e) {
			throw new RuntimeException("Failed to click on the element: " + element, e);
		}
	}


	/**
	 * Sends the specified text to the given WebElement after ensuring it is visible.
	 *
	 * @param element The WebElement to send text to.
	 * @param text    The text to input into the WebElement.
	 */
	public void sendKeys(WebElement element, String text) {
		if (element == null) {
			throw new IllegalArgumentException("The WebElement cannot be null.");
		}
		if (text == null) {
			throw new IllegalArgumentException("The text to send cannot be null.");
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element)); // Ensure element is visible
		element.clear(); // Clear any existing text
		element.sendKeys(text); // Send the input text
	}


	/**
	 * Scrolls the specified WebElement into view and adjusts for fixed headers or footers if needed.
	 *
	 * @param element The WebElement to scroll to.
	 */
	public void scrollToElement(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("The WebElement cannot be null.");
		}
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Scroll the element into view, centering it in the viewport
			js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);

			// Adjust for headers or footers if applicable
			js.executeScript("window.scrollBy(0, -50);"); // Offset by 50px
		} catch (Exception e) {
			throw new RuntimeException("Failed to scroll to the element: " + element, e);
		}
	}


	/**
	 * Retrieves the visible text of the specified WebElement after ensuring it is visible.
	 *
	 * @param element The WebElement from which to retrieve the text.
	 * @return The visible text of the WebElement.
	 */
	public String getText(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("The WebElement cannot be null.");
		}
		waitForWebElementToVisible(element); // Ensure the element is visible
		return element.getText();
	}


	/**
	 * Performs a mouse hover action on the specified WebElement.
	 *
	 * @param element The WebElement to hover over.
	 */
	public void mouseHover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	/**
	 * Selects an option in a dropdown menu by its visible text.
	 *
	 * @param element The dropdown WebElement.
	 * @param text    The visible text of the option to select.
	 */
	public void selectDropdownByVisibleText(WebElement element, String text) {
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(text);
	}

	/**
	 * Clears the text from the specified input field located by the given locator.
	 *
	 * @param locator The By locator of the text field to clear.
	 */
	public void clearTextField(By locator) {
		WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Ensure the element is visible
		element.clear(); // Clear the text field
	}

	/**
	 * Waits until the element located by the given locator contains the specified text.
	 *
	 * @param locator         The By locator of the element to check.
	 * @param text            The expected text to be present in the element.
	 * @param timeoutInSeconds The maximum time to wait for the text to appear.
	 */
	public void waitForElementToContainText(By locator, String text, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	/**
	 * Retrieves the current URL of the web page loaded in the browser.
	 *
	 * @return The current URL as a String.
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Navigates the browser to the specified URL.
	 *
	 * @param url The URL to navigate to.
	 */
	public void navigateToUrl(String url) {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("The URL cannot be null or empty.");
		}
		driver.navigate().to(url);
	}

	/**
	 * Checks if an alert is present on the page.
	 *
	 * @return True if an alert is present, otherwise false.
	 */
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert(); // Attempt to switch to the alert
			return true;
		} catch (NoAlertPresentException e) {
			return false; // No alert present
		} catch (Exception e) {
			throw new RuntimeException("Error occurred while checking for alert presence", e);
		}
	}

	/**
	 * Performs a double-click action on the element located by the given locator.
	 *
	 * @param locator The By locator of the element to double-click.
	 */
	public void doubleClickElement(By locator) {
		if (locator == null) {
			throw new IllegalArgumentException("The locator cannot be null.");
		}
		try {
			WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(locator)); // Ensure element is clickable
			Actions actions = new Actions(driver);
			actions.doubleClick(element).perform(); // Perform double-click
		} catch (Exception e) {
			throw new RuntimeException("Failed to double-click on the element located by: " + locator, e);
		}
	}

	/**
	 * Performs a right-click (context click) action on the element located by the given locator.
	 *
	 * @param locator The By locator of the element to right-click.
	 */
	public void rightClickElement(By locator) {
		if (locator == null) {
			throw new IllegalArgumentException("The locator cannot be null.");
		}
		try {
			WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(locator)); // Ensure element is clickable
			Actions actions = new Actions(driver);
			actions.contextClick(element).perform(); // Perform right-click
		} catch (Exception e) {
			throw new RuntimeException("Failed to right-click on the element located by: " + locator, e);
		}
	}

	/**
	 * Retrieves all the visible options from a dropdown as a list of strings.
	 *
	 * @param locator The By locator of the dropdown element.
	 * @return A list of strings representing the text of all dropdown options.
	 */
	public List<String> getAllDropdownOptions(By locator) {
		if (locator == null) {
			throw new IllegalArgumentException("The locator cannot be null.");
		}
		try {
			WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Wait for the dropdown to be visible
			Select select = new Select(dropdown);
			List<WebElement> options = select.getOptions(); // Get all options
			List<String> optionTexts = new ArrayList<>();
			for (WebElement option : options) {
				optionTexts.add(option.getText()); // Add option text to the list
			}
			return optionTexts;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve dropdown options for locator: " + locator, e);
		}
	}

	/**
	 * Waits until the page has fully loaded by checking the document's ready state.
	 *
	 * @param timeoutInSeconds The maximum time to wait for the page to load.
	 */
	public void waitForPageLoadComplete(int timeoutInSeconds) {
		if (timeoutInSeconds <= 0) {
			throw new IllegalArgumentException("Timeout must be greater than zero.");
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
			wait.until(webDriver -> ((JavascriptExecutor) driver)
					.executeScript("return document.readyState").equals("complete")); // Wait for document readiness
		} catch (Exception e) {
			throw new RuntimeException("Page did not load within " + timeoutInSeconds + " seconds.", e);
		}
	}



	public CartPage goToCart(){
		goToCartBtn.click();
		return new CartPage(driver);
	}
	
	public OrdersHistory goToOrderHistory(){
		goToOrdersHistoryBtn.click();
		return new OrdersHistory(driver);
	}
}
