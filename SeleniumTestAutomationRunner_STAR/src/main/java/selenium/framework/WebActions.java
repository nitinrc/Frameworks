package selenium.framework;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import interfaces.*;
import springBeans.FlightBookingConfig;
import tests.Runner;

public class WebActions extends FindElement implements UserActions {
	public WebDriver getDriver() {
		DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
		return objDesiredCapabilities.getDriver();
	}
	
	public void navigate(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().to(mapElementParameters.get("Input"));
	}
	
	public void getCurrentUrl(HashMap<String, String> mapElementParameters) {
		getDriver().getCurrentUrl();
	}
	
	public void navigateBack(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().back();
	}
	
	public void navigateForward(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().forward();
	}
	
	public void refresh(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().refresh();
	}
	
	public void getTitle(HashMap<String, String> mapElementParameters) {
		getDriver().getTitle();
	}
	
	public void windowMaximize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().maximize();
	}
	
	public void windowFullScreen(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().fullscreen();
	}
	
	public void windowGetSize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().getSize();//getWidth(),getHeight()
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
	}
	
	public void windowGetPosition(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().getPosition();//getX(),getY()
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
	}
	
	//public void sendKeys(String... args) {
	public void sendKeys(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		element.click();
		element.clear();
		element.sendKeys(mapElementParameters.get("Input"));
	}
	
	public void click(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		element.click();
	}
	
	public void getText(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		element.getText();
		System.out.println("Retrieved Text: "+element.getText());
	}
	
	public void scrollIntoView(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void dragAndDrop(HashMap<String, String> mapElementParameters) {
		WebElement startElement = findElement(mapElementParameters.get("Locator1"), mapElementParameters.get("LocatorType1"), mapElementParameters.get("ExpectedCondition1"), mapElementParameters.get("Timeout1"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		WebElement endElement = findElement(mapElementParameters.get("Locator2"), mapElementParameters.get("LocatorType2"), mapElementParameters.get("ExpectedCondition2"), mapElementParameters.get("Timeout2"));
		if (Runner.runStatus.equals("FAIL")) {
			System.exit(0);
		}
		Actions builder = new Actions(getDriver());
		Action dragAndDrop = builder.clickAndHold(startElement)
		   .moveToElement(endElement)
		   .release(endElement)
		   .build();
		dragAndDrop.perform();
	}
	
	public void switchToDefaultContent(HashMap<String, String> mapElementParameters) {
		getDriver().switchTo().defaultContent();
	}
	
	public void switchToFrame(HashMap<String, String> mapElementParameters) {
		try {
			//WebElement iframe = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			WebElement iframe = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
			if (Runner.runStatus.equals("FAIL")) {
				System.exit(0);
			}
			try {
				getDriver().switchTo().frame(iframe);
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void switchBrowser(HashMap<String, String> mapElementParameters) {
		for (String handle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(handle);
		}
	}
	
	public void closeBrowsers(HashMap<String, String> mapElementParameters) {
		int countBrowsers = getDriver().getWindowHandles().size();
		System.out.println("Open Browser Tab Count: "+countBrowsers);
		int itr = 1;
		for (String handle : getDriver().getWindowHandles()) {
			if (itr < countBrowsers) {
				getDriver().close();
			}
			itr++;
		}
		switchBrowser(mapElementParameters);
	}
	
	public void alertClose(HashMap<String, String> mapElementParameters) {
		try {
			//WebElement alertClose = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			WebElement alertClose = findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
			if (Runner.runStatus.equals("FAIL")) {
				System.exit(0);
			}
			try {
				alertClose.click();
				switchToDefaultContent(null);
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void alertAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(getDriver(),Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String text = alert.getText();
		alert.accept();
	}
	
	public void alertDismiss(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(getDriver(),Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = getDriver().switchTo().alert();
		String text = alert.getText();
		alert.dismiss();
	}
	
	public void promptAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(getDriver(),Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(mapElementParameters.get("Input"));
		alert.accept();
	}
}
