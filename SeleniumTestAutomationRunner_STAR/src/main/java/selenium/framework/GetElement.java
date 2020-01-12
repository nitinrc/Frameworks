package selenium.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetElement {
	@SuppressWarnings("unchecked")
	public WebElement getElement(String locator, String locatorType, String expectedCondition, String timeout) {
		WebDriverWait wait = new WebDriverWait(DesiredCapabilities.driver,Integer.parseInt(timeout));
		WebElement element = null;
		Method method1 = null;
		Method method2 = null;
		try {
			method1 = ExpectedConditions.class.getMethod(expectedCondition, By.class);
			method2 = By.class.getMethod(locatorType, String.class);
		} catch (NoSuchMethodException e1) {e1.printStackTrace();} catch (SecurityException e1) {e1.printStackTrace();}
		try {
			element = wait.until((Function<? super WebDriver, WebElement>) method1.invoke(ExpectedConditions.class, (By) method2.invoke(By.class, locator)));
		} catch (IllegalAccessException e) {e.printStackTrace();} catch (IllegalArgumentException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();}
		return element;
	}
	//WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a/h3")));
}
