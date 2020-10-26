package com.star.core;

import com.star.Runner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class FindElement {
	Runner runner = Config.context.getBean(Runner.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);

	public WebElement findElement(String locator, String locatorType, String expectedCondition, String timeout) {
		WebElement element = getElement(locator, locatorType, expectedCondition, timeout);
		if (!(element.isEnabled()) || !(element.isDisplayed())) {
			runner.setRunStatus(RunStatus.FAIL);
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	public WebElement getElement(String locator, String locatorType, String expectedCondition, String timeout) {
		WebDriverWait wait = new WebDriverWait(browserConfig.getDriver(), Integer.parseInt(timeout));
		Method method1;
		Method method2;
		try {
			method1 = ExpectedConditions.class.getMethod(expectedCondition, By.class);
			method2 = By.class.getMethod(locatorType, String.class);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return null;
		} catch (SecurityException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return null;
		}
		try {
			WebElement element = wait.until((Function<? super WebDriver, WebElement>) method1.invoke(ExpectedConditions.class, method2.invoke(By.class, locator)));
			return element;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		runner.setRunStatus(RunStatus.FAIL);
		return null;
	}
}
