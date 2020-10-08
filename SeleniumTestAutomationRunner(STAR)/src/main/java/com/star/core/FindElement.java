package com.star.core;

import com.star.Runner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.star.pages.Alerts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class FindElement {
	public WebElement findElement(String locator, String locatorType, String expectedCondition, String timeout) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement element = getElement(locator, locatorType, expectedCondition, timeout);
		if (!(element.isEnabled())) {
			if (!(element.isDisplayed())) {
				Alerts alert = Config.context.getBean(Alerts.class);
				alert.alertClose();
				element = getElement(locator, locatorType, expectedCondition, timeout);
				if (!(element.isEnabled())) {
					runner.setRunStatus(RunStatus.FAIL);
				}
			} else {
				runner.setRunStatus(RunStatus.FAIL);
			}
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	public WebElement getElement(String locator, String locatorType, String expectedCondition, String timeout) {
		Runner runner = Config.context.getBean(Runner.class);
		BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
		WebDriverWait wait = new WebDriverWait(browserConfig.getDriver(), Integer.parseInt(timeout));
		
		WebElement element = null;
		Method method1 = null;
		Method method2 = null;
		try {
			method1 = ExpectedConditions.class.getMethod(expectedCondition, By.class);
			method2 = By.class.getMethod(locatorType, String.class);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			System.exit(0);
		} catch (SecurityException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			System.exit(0);
		}
		try {
			element = wait.until((Function<? super WebDriver, WebElement>) method1.invoke(ExpectedConditions.class, method2.invoke(By.class, locator)));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			System.exit(0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			System.exit(0);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			System.exit(0);
		}
		return element;
	}
}
