package selenium.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Alerts;
import tests.Runner;

public class FindElement {
	@SuppressWarnings("unchecked")
	public WebElement findElement(String locator, String locatorType, String expectedCondition, String timeout) {
		new Alerts().actionAlert();
		//Float.parseFloat
		WebDriverWait wait = new WebDriverWait(DesiredCapabilities.driver,Integer.parseInt(timeout));
		WebElement element = null;
		Method method1 = null;
		Method method2 = null;
		try {
			method1 = ExpectedConditions.class.getMethod(expectedCondition, By.class);
			method2 = By.class.getMethod(locatorType, String.class);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			Runner.runStatus = "FAIL";
			System.exit(0);
		} catch (SecurityException e1) {
			e1.printStackTrace();
			Runner.runStatus = "FAIL";
			System.exit(0);
		}
		try {
			element = wait.until((Function<? super WebDriver, WebElement>) method1.invoke(ExpectedConditions.class, (By) method2.invoke(By.class, locator)));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Runner.runStatus = "FAIL";
			System.exit(0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Runner.runStatus = "FAIL";
			System.exit(0);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Runner.runStatus = "FAIL";
			System.exit(0);
		}
		return element;
	}
}
