package selenium.framework;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import interfaces.*;

public class WebActions extends GetElement implements UserActions {
	public void navigate(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.navigate().to(mapElementParameters.get("Input"));
	}
	
	public void getCurrentUrl() {
		DesiredCapabilities.driver.getCurrentUrl();
	}
	
	public void navigateBack() {
		DesiredCapabilities.driver.navigate().back();
	}
	
	public void navigateForward() {
		DesiredCapabilities.driver.navigate().forward();
	}
	
	public void refresh() {
		DesiredCapabilities.driver.navigate().refresh();
	}
	
	public void getTitle() {
		DesiredCapabilities.driver.getTitle();
	}
	
	public void windowMaximize() {
		DesiredCapabilities.driver.manage().window().maximize();
	}
	
	public void windowFullScreen() {
		DesiredCapabilities.driver.manage().window().fullscreen();
	}
	
	public void windowGetSize() {
		DesiredCapabilities.driver.manage().window().getSize();//getWidth(),getHeight()
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		
		DesiredCapabilities.driver.manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
	}
	
	public void windowGetPosition() {
		DesiredCapabilities.driver.manage().window().getPosition();//getX(),getY()
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		DesiredCapabilities.driver.manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")), Integer.parseInt(mapElementParameters.get("Y"))));
	}
	
	//public void sendKeys(String... args) {
	public void sendKeys(HashMap<String, String> mapElementParameters) {
		WebElement element = getElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		element.click();
		element.clear();
		element.sendKeys(mapElementParameters.get("Input"));
	}
	
	public void click(HashMap<String, String> mapElementParameters) {
		WebElement element = getElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		element.click();
	}
	
	public void getText(HashMap<String, String> mapElementParameters) {
		WebElement element = getElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		element.getText();
		System.out.println("Retrieved Text: "+element.getText());
	}
	
	public void scrollIntoView(HashMap<String, String> mapElementParameters) {
		WebElement element = getElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
		((JavascriptExecutor) DesiredCapabilities.driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void dragAndDrop(HashMap<String, String> mapElementParameters) {
		WebElement startElement = getElement(mapElementParameters.get("Locator1"), mapElementParameters.get("LocatorType1"), mapElementParameters.get("ExpectedCondition1"), mapElementParameters.get("Timeout1"));
		WebElement endElement = getElement(mapElementParameters.get("Locator2"), mapElementParameters.get("LocatorType2"), mapElementParameters.get("ExpectedCondition2"), mapElementParameters.get("Timeout2"));
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
	
	public void switchToFrame(String frameName) {
		switchToDefaultContent();
		DesiredCapabilities.driver.switchTo().frame(frameName);
	}
	
	public void switchBrowser() {
		for (String handle : DesiredCapabilities.driver.getWindowHandles()) {
			DesiredCapabilities.driver.switchTo().window(handle);
		}
	}
	
	public void closeBrowsers(int keepOpen) {
		int countBrowsers = DesiredCapabilities.driver.getWindowHandles().size();
		System.out.println("Open Browser Tab Count: "+countBrowsers);
		int itr = 1;
		for (String handle : DesiredCapabilities.driver.getWindowHandles()) {
			if (itr <= (countBrowsers - keepOpen)) {
				DesiredCapabilities.driver.close();
			}
			itr++;
		}
		switchBrowser();
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
