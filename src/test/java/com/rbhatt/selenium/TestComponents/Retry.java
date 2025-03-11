package com.rbhatt.selenium.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	int count = 0;
	int maxTry =2;

	// ThreadLocal variable to track retry status
	private static final ThreadLocal<Boolean> isRetryMode = ThreadLocal.withInitial(() -> false);

	@Override
	public boolean retry(ITestResult result) {
		if(count<maxTry){
			count++;
			isRetryMode.set(true); // Mark retry mode
			return true;
		}
		isRetryMode.set(false); // Reset retry mode for non-retry cases
		return false;
	}

	public static boolean isRetryMode() {
		return isRetryMode.get();
	}
}
