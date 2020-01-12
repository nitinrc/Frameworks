package selenium.framework;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import interfaces.*;
import tests.Runner;

public class WebActions extends FindElement implements UserActions {
	public void navigate(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.navigate().to(mapElementParameters.get("Input"));
	}
	
	public void getCurrentUrl(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.getCurrentUrl();
	}
	
	public void navigateBack(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.navigate().back();
	}
	
	public void navigateForward(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.navigate().forward();
	}
	
	public void refresh(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.navigate().refresh();
	}
	
	public void getTitle(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.getTitle();
	}
	
	public void windowMaximize(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().maximize();
	}
	
	public void windowFullScreen(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().fullscreen();
	}
	
	public void windowGetSize(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().getSize();//getWidth(),getHeight()
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		
		DesiredCapabilities.driver.manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
	}
	
	public void windowGetPosition(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().getPosition();//getX(),getY()
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
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
		((JavascriptExecutor) DesiredCapabilities.driver).executeScript("arguments[0].scrollIntoView(true);", element);
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
		Actions builder = new Actions(DesiredCapabilities.driver);
		Action dragAndDrop = builder.clickAndHold(startElement)
		   .moveToElement(endElement)
		   .release(endElement)
		   .build();
		dragAndDrop.perform();
	}
	
	public void switchToDefaultContent() {
		DesiredCapabilities.driver.switchTo().defaultContent();
	}
	
	public void switchToFrame(String xpath, int timeout) {
		try {
			WebElement iframe = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			try {
				DesiredCapabilities.driver.switchTo().frame(iframe);
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void switchBrowser(HashMap<String, String> mapElementParameters) {
		for (String handle : DesiredCapabilities.driver.getWindowHandles()) {
			DesiredCapabilities.driver.switchTo().window(handle);
		}
	}
	
	public void closeBrowsers(HashMap<String, String> mapElementParameters) {
		int countBrowsers = DesiredCapabilities.driver.getWindowHandles().size();
		System.out.println("Open Browser Tab Count: "+countBrowsers);
		int itr = 1;
		for (String handle : DesiredCapabilities.driver.getWindowHandles()) {
			if (itr < countBrowsers) {
				DesiredCapabilities.driver.close();
			}
			itr++;
		}
		switchBrowser(mapElementParameters);
	}
	
	public void alertElementClick(String locator, int timeout) {
		try {
			WebElement popupClose = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			try {
				popupClose.click();
				switchToDefaultContent();
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void alertAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(DesiredCapabilities.driver,Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String text = alert.getText();
		alert.accept();
	}
	
	public void alertDismiss(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(DesiredCapabilities.driver,Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = DesiredCapabilities.driver.switchTo().alert();
		String text = alert.getText();
		alert.dismiss();
	}
	
	public void promptAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(DesiredCapabilities.driver,Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(mapElementParameters.get("Input"));
		alert.accept();
	}
}
