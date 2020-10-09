package com.star.core;

import com.star.Runner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
	Runner runner = Config.context.getBean(Runner.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
	ReadPropertyFile readPropertyFile = Config.context.getBean(ReadPropertyFile.class);

	public WebDriver getDriver() {
		try {
			return browserConfig.getDriver();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return null;
		}
	}
	
	public void navigate(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().to(mapElementParameters.get("Input"));
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void getCurrentUrl(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void navigateBack(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void navigateForward(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().forward();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void refresh(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public String getTitle(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().getTitle();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		return StringUtils.EMPTY;
	}
	
	public void windowMaximize(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void windowFullScreen(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().fullscreen();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public Dimension windowGetSize(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().manage().window().getSize(); //getWidth(),getHeight()
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		return null;
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")),
					Integer.parseInt(mapElementParameters.get("Y"))));
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public Point windowGetPosition(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().manage().window().getPosition(); //getX(),getY()
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		return null;
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")),
					Integer.parseInt(mapElementParameters.get("Y"))));
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	//public void sendKeys(String... args) {
	public void sendKeys(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return;
		}
		try {
			element.click();
			element.clear();
			element.sendKeys(mapElementParameters.get("Input"));
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void click(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return;
		}
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public String getText(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return StringUtils.EMPTY;
		}
		takeScreenshot();
		try {
			log.info("Retrieved Text: {}", element.getText());
			return element.getText();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		return StringUtils.EMPTY;
	}
	
	public void scrollIntoView(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters.get("Locator"),
				mapElementParameters.get("LocatorType"),
				mapElementParameters.get("ExpectedCondition"),
				mapElementParameters.get("Timeout"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return;
		}
		try {
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void dragAndDrop(HashMap<String, String> mapElementParameters) {
		WebElement startElement = findElement(mapElementParameters.get("Locator1"),
				mapElementParameters.get("LocatorType1"),
				mapElementParameters.get("ExpectedCondition1"),
				mapElementParameters.get("Timeout1"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return;
		}
		WebElement endElement = findElement(mapElementParameters.get("Locator2"),
				mapElementParameters.get("LocatorType2"),
				mapElementParameters.get("ExpectedCondition2"),
				mapElementParameters.get("Timeout2"));
		if (runner.getRunStatus().equals(RunStatus.FAIL)) {
			return;
		}
		try {
			Actions builder = new Actions(getDriver());
			Action dragAndDrop = builder.clickAndHold(startElement)
					.moveToElement(endElement)
					.release(endElement)
					.build();
			dragAndDrop.perform();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void switchToDefaultContent(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void switchToFrame(HashMap<String, String> mapElementParameters) {
		try {
			//WebElement iframe = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			WebElement iframe = findElement(mapElementParameters.get("Locator"),
					mapElementParameters.get("LocatorType"),
					mapElementParameters.get("ExpectedCondition"),
					mapElementParameters.get("Timeout"));
			if (runner.getRunStatus().equals(RunStatus.FAIL)) {
				return;
			}
			try {
				getDriver().switchTo().frame(iframe);
				takeScreenshot();
			} catch (Exception e) {
				e.printStackTrace();
				runner.setRunStatus(RunStatus.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
	}
	
	public void switchBrowser(HashMap<String, String> mapElementParameters) {
		try {
			for (String handle : getDriver().getWindowHandles()) {
				getDriver().switchTo().window(handle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
	}
	
	public void closeBrowsers(HashMap<String, String> mapElementParameters) {
		int countBrowsers = getDriver().getWindowHandles().size();
		log.info("Open Browser Tab Count: {}", countBrowsers);
		int itr = 1;
		try {
			for (String handle : getDriver().getWindowHandles()) {
				if (itr < countBrowsers) {
					getDriver().close();
				}
				itr++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		takeScreenshot();
		switchBrowser(mapElementParameters);
	}
	
	public void alertClose(HashMap<String, String> mapElementParameters) {
		try {
			//WebElement alertClose = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			WebElement alertClose = findElement(mapElementParameters.get("Locator"),
					mapElementParameters.get("LocatorType"),
					mapElementParameters.get("ExpectedCondition"),
					mapElementParameters.get("Timeout"));
			if (runner.getRunStatus().equals(RunStatus.FAIL)) {
				return;
			}
			try {
				alertClose.click();
				takeScreenshot();
				switchToDefaultContent(null);
			} catch (Exception e) {
				e.printStackTrace();
				runner.setRunStatus(RunStatus.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
	}
	
	public void alertAccept(HashMap<String, String> mapElementParameters) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			//String text = alert.getText();
			takeScreenshot();
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		}
		takeScreenshot();
	}
	
	public void alertDismiss(HashMap<String, String> mapElementParameters) {
		try {
			//WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = getDriver().switchTo().alert();
			//String text = alert.getText();
			takeScreenshot();
			alert.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		}
		takeScreenshot();
	}
	
	public void promptAccept(HashMap<String, String> mapElementParameters) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.sendKeys(mapElementParameters.get("Input"));
			takeScreenshot();
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		}
		takeScreenshot();
	}
	
	public void takeScreenshot() {
		File file = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file,
					new File(readPropertyFile.readProperty().getProperty("ScreenshotPath")
							+ "\\Screenshot_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())
							+ "_" + browserConfig.getBrowser().toUpperCase() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
	}
}
