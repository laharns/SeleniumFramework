package com.rbhatt.selenium.tests.ui;

import com.rbhatt.selenium.Base.BaseTest;
import com.rbhatt.selenium.TestComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationsTest extends BaseTest {
	
	@Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException {
		
		//1. Perform Login with incorrect Password
		landingPage.loginAction("risshilbhatt@gmail.com", "Test@1234");
		Assert.assertEquals("Incorrect email or password.",landingPage.getLoginErrorMessage());
		
	}
}
