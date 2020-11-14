package com.star.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Function;

public class FindElement {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);

	public WebElement findElement(HashMap<String, String> mapElementParameters) {
		WebElement element = null;
		if (mapElementParameters.get("FindElementBy").equals("ExpectedConditions")) {
			element = findElementByExpectedConditionsByReflection(mapElementParameters);
		} else if (mapElementParameters.get("FindElementBy").equals("id")) {
			element = browserConfig.getDriver().findElement(By.id(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("name")) {
			element = browserConfig.getDriver().findElement(By.name(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("className")) {
			element = browserConfig.getDriver().findElement(By.className(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("cssSelector")) {
			element = browserConfig.getDriver().findElement(By.cssSelector(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("xpath")) {
			element = browserConfig.getDriver().findElement(By.xpath(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("linkText")) {
			element = browserConfig.getDriver().findElement(By.linkText(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("partialLinkText")) {
			element = browserConfig.getDriver().findElement(By.partialLinkText(mapElementParameters.get("Locator")));
		} else if (mapElementParameters.get("FindElementBy").equals("tagName")) {
			element = browserConfig.getDriver().findElement(By.tagName(mapElementParameters.get("Locator")));
		}

		if (!(element.isEnabled()) || !(element.isDisplayed())) {
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.ELEMENT_NOT_READY_ERROR);
		}
		return element;
	}

	public WebElement findElementByExpectedConditionsByReflection(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(browserConfig.getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
		Method method1;
		Method method2;
		try {
			method1 = ExpectedConditions.class.getMethod(mapElementParameters.get("ExpectedCondition"), By.class);
			method2 = By.class.getMethod(mapElementParameters.get("LocatorType"), String.class);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.FIND_ELEMENT_BY_EXPECTED_CONDITIONS_ERROR);
			return null;
		} catch (SecurityException e1) {
			e1.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.FIND_ELEMENT_BY_EXPECTED_CONDITIONS_ERROR);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.FIND_ELEMENT_BY_EXPECTED_CONDITIONS_ERROR);
			return null;
		}
		try {
			WebElement element = wait.until((Function<? super WebDriver, WebElement>) method1.invoke(ExpectedConditions.class, method2.invoke(By.class, mapElementParameters.get("Locator"))));
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
		resultStatus.setRunStatus(RunStatus.FAIL);
		resultStatus.setFailureReason(FailureReasons.FIND_ELEMENT_BY_EXPECTED_CONDITIONS_ERROR);
		return null;
	}
}
