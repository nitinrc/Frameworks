package com.star.core;

import com.star.Runner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@Slf4j
public class WebActionsImpl extends FindElement implements WebActions {
	public WebDriver getDriver() {
		BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
		return browserConfig.getDriver();
	}
	
	public void navigate(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().to(mapElementParameters.get("Input"));
		takeScreenshot();
	}
	
	public void getCurrentUrl(HashMap<String, String> mapElementParameters) {
		getDriver().getCurrentUrl();
		takeScreenshot();
	}
	
	public void navigateBack(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().back();
		takeScreenshot();
	}
	
	public void navigateForward(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().forward();
		takeScreenshot();
	}
	
	public void refresh(HashMap<String, String> mapElementParameters) {
		getDriver().navigate().refresh();
		takeScreenshot();
	}
	
	public void getTitle(HashMap<String, String> mapElementParameters) {
		getDriver().getTitle();
		takeScreenshot();
	}
	
	public void windowMaximize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().maximize();
		takeScreenshot();
	}
	
	public void windowFullScreen(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().fullscreen();
		takeScreenshot();
	}
	
	public void windowGetSize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().getSize(); //getWidth(),getHeight()
		takeScreenshot();
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")),
				Integer.parseInt(mapElementParameters.get("Y"))));
		takeScreenshot();
	}
	
	public void windowGetPosition(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().getPosition(); //getX(),getY()
		takeScreenshot();
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		getDriver().manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")),
				Integer.parseInt(mapElementParameters.get("Y"))));
		takeScreenshot();
	}
	
	//public void sendKeys(String... args) {
	public void sendKeys(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		element.click();
		element.clear();
		element.sendKeys(mapElementParameters.get("Input"));
		takeScreenshot();
	}
	
	public void click(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		element.click();
		takeScreenshot();
	}
	
	public void getText(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		element.getText();
		log.info("Retrieved Text: {}", element.getText());
		takeScreenshot();
	}
	
	public void scrollIntoView(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		takeScreenshot();
	}
	
	public void dragAndDrop(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		WebElement startElement = findElement(mapElementParameters.get("Locator1"),
				mapElementParameters.get("LocatorType1"),
				mapElementParameters.get("ExpectedCondition1"),
				mapElementParameters.get("Timeout1"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		WebElement endElement = findElement(mapElementParameters.get("Locator2"),
				mapElementParameters.get("LocatorType2"),
				mapElementParameters.get("ExpectedCondition2"),
				mapElementParameters.get("Timeout2"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			System.exit(0);
		}
		Actions builder = new Actions(getDriver());
		Action dragAndDrop = builder.clickAndHold(startElement)
		   .moveToElement(endElement)
		   .release(endElement)
		   .build();
		dragAndDrop.perform();
		takeScreenshot();
	}
	
	public void switchToDefaultContent(HashMap<String, String> mapElementParameters) {
		getDriver().switchTo().defaultContent();
		takeScreenshot();
	}
	
	public void switchToFrame(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		try {
			//WebElement iframe = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			WebElement iframe = findElement(mapElementParameters.get("Locator"),
					mapElementParameters.get("LocatorType"),
					mapElementParameters.get("ExpectedCondition"),
					mapElementParameters.get("Timeout"));
			if (runner.getRunStatus().equals(RunStatus.FAIL)) {
				System.exit(0);
			}
			try {
				getDriver().switchTo().frame(iframe);
				takeScreenshot();
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void switchBrowser(HashMap<String, String> mapElementParameters) {
		for (String handle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(handle);
		}
		takeScreenshot();
	}
	
	public void closeBrowsers(HashMap<String, String> mapElementParameters) {
		int countBrowsers = getDriver().getWindowHandles().size();
		log.info("Open Browser Tab Count: {}", countBrowsers);
		int itr = 1;
		for (String handle : getDriver().getWindowHandles()) {
			if (itr < countBrowsers) {
				getDriver().close();
			}
			itr++;
		}
		takeScreenshot();
		switchBrowser(mapElementParameters);
	}
	
	public void alertClose(HashMap<String, String> mapElementParameters) {
		Runner runner = Config.context.getBean(Runner.class);
		try {
			//WebElement alertClose = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			WebElement alertClose = findElement(mapElementParameters.get("Locator"),
					mapElementParameters.get("LocatorType"),
					mapElementParameters.get("ExpectedCondition"),
					mapElementParameters.get("Timeout"));
			if (runner.getRunStatus().equals(RunStatus.FAIL)) {
				System.exit(0);
			}
			try {
				alertClose.click();
				takeScreenshot();
				switchToDefaultContent(null);
			} catch (Exception e) {}
		} catch (Exception e) {}
	}
	
	public void alertAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		//String text = alert.getText();
		takeScreenshot();
		alert.accept();
	}
	
	public void alertDismiss(HashMap<String, String> mapElementParameters) {
		//WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = getDriver().switchTo().alert();
		//String text = alert.getText();
		takeScreenshot();
		alert.dismiss();
	}
	
	public void promptAccept(HashMap<String, String> mapElementParameters) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(mapElementParameters.get("Input"));
		takeScreenshot();
		alert.accept();
	}
	
	public void takeScreenshot() {
		BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
		ReadPropertyFile readPropertyFile = Config.context.getBean(ReadPropertyFile.class);
		File file = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file,
					new File(readPropertyFile.readProperty().getProperty("ScreenshotPath")
							+ "\\Screenshot_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())
							+ "_" + browserConfig.getBrowser().toUpperCase() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
