package com.star.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@Slf4j
public class WebActionsImpl extends FindElement implements WebActions {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
	ReadPropertyFile readPropertyFile = Config.context.getBean(ReadPropertyFile.class);

	public WebDriver getDriver() {
		try {
			return browserConfig.getDriver();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.EXCEL_TEST_DATA_FETCH_ERROR);
			Assert.assertTrue(false);
			return null;
		}
	}
	
	public void navigate(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().to(mapElementParameters.get("Input"));
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.URL_NAVIGATE_ERROR + ": " + mapElementParameters.get("Input"));
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public String getCurrentUrl(HashMap<String, String> mapElementParameters) {
        takeScreenshot();
		try {
			return getDriver().getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.URL_NAVIGATE_ERROR);
			Assert.assertTrue(false);
		}
        return StringUtils.EMPTY;
	}
	
	public void navigateBack(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.URL_NAVIGATE_BACK_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void navigateForward(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().forward();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.URL_NAVIGATE_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void refresh(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.PAGE_REFRESH_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public String getTitle(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().getTitle();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.GET_PAGE_TITLE_ERROR);
			Assert.assertTrue(false);
		}
		return StringUtils.EMPTY;
	}
	
	public void windowMaximize(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_MAXIMIZE_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void windowFullScreen(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().fullscreen();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_FULL_SCREEN_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public Dimension windowGetSize(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().manage().window().getSize(); //getWidth(),getHeight()
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_GET_SIZE_ERROR);
			Assert.assertTrue(false);
		}
		return null;
	}
	
	public void windowSetSize(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().setSize(new Dimension(Integer.parseInt(mapElementParameters.get("X")),
					Integer.parseInt(mapElementParameters.get("Y"))));
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_SET_SIZE_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public Point windowGetPosition(HashMap<String, String> mapElementParameters) {
		takeScreenshot();
		try {
			return getDriver().manage().window().getPosition(); //getX(),getY()
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_GET_POSITION_ERROR);
			Assert.assertTrue(false);
		}
		return null;
	}
	
	public void windowSetPosition(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().manage().window().setPosition(new Point(Integer.parseInt(mapElementParameters.get("X")),
					Integer.parseInt(mapElementParameters.get("Y"))));
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.WINDOW_SET_POSITION_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}

	//public void sendKeys(String... args) {
	public void findElementAndSendKeys(HashMap<String, String> mapElementParameters, String input) {
		WebElement element = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		log.info("Attempting to enter text: {} on element using locator: {}", input, mapElementParameters.get("Locator"));
		sendKeys(element, input);
	}

	public void sendKeys(WebElement element, String input) {
		try {
			element.click();
			element.clear();
			element.sendKeys(input);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SEND_KEYS_ERROR + " | input: " + input);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void findElementAndClick(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		log.info("Attempting to click on element using locator: {}", mapElementParameters.get("Locator"));
		click(element);
	}

	public void click(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.CLICK_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}

	public String findElementAndGetText(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return StringUtils.EMPTY;
		}
		log.info("Attempting to get text from element using locator: {}", mapElementParameters.get("Locator"));
		return getText(element);
	}
	
	public String getText(WebElement element) {
		takeScreenshot();
		try {
			log.info("Retrieved Text: {}", element.getText());
			return element.getText();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.GET_TEXT_ERROR);
			Assert.assertTrue(false);
		}
		return StringUtils.EMPTY;
	}

	public void findElementAndScrollIntoView(HashMap<String, String> mapElementParameters) {
		WebElement element = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		log.info("Attempting to scroll element into view using locator: {}", mapElementParameters.get("Locator"));
		scrollIntoView(element);
	}
	
	public void scrollIntoView(WebElement element) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SCROLL_INTO_VIEW_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}

	public void findElementAndDragAndDrop(HashMap<String, String> mapFromElementParameters, HashMap<String, String> mapToElementParameters) {
		WebElement fromElement = findElement(mapFromElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		WebElement toElement = findElement(mapToElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		log.info("Attempting to drag and drop from element using locator: {} to element using locator: {}",
				mapFromElementParameters.get("Locator"), mapToElementParameters.get("Locator"));
		dragAndDrop(fromElement, toElement);
	}
	
	public void dragAndDrop(WebElement fromElement, WebElement toElement) {
		try {
			Actions builder = new Actions(getDriver());
			Action dragAndDrop = builder.clickAndHold(fromElement)
					.moveToElement(toElement)
					.release(toElement)
					.build();
			dragAndDrop.perform();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.DRAG_AND_DROP_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void switchToDefaultContent(HashMap<String, String> mapElementParameters) {
		try {
			getDriver().switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SWITCH_TO_DEFAULT_CONTENT_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}

	public void findAndSwitchToFrame(HashMap<String, String> mapElementParameters) {
		//WebElement iframe = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		WebElement iframe = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason());
			Assert.assertTrue(false);
			return;
		}
		log.info("Attempting to switch to iframe using locator: {}", mapElementParameters.get("Locator"));
		switchToFrame(iframe);
	}
	
	public void switchToFrame(WebElement iframe) {
		try {
			getDriver().switchTo().frame(iframe);
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SWITCH_TO_FRAME_ERROR);
			Assert.assertTrue(false);
		}
	}
	
	public void switchBrowser(HashMap<String, String> mapElementParameters) {
		try {
			for (String handle : getDriver().getWindowHandles()) {
				getDriver().switchTo().window(handle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SWITCH_BROWSER_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
	}
	
	public void closeBrowsers(HashMap<String, String> mapElementParameters) {
		int countBrowsers = getDriver().getWindowHandles().size();
		log.info("Open Browser Tab Count: {}", countBrowsers);
		int indexBrowser = 1;
		try {
			for (String handle : getDriver().getWindowHandles()) {
				if (indexBrowser < countBrowsers) {
					getDriver().close();
				}
				indexBrowser++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.CLOSE_BROWSER_ERROR);
			Assert.assertTrue(false);
		}
		takeScreenshot();
		switchBrowser(mapElementParameters);
	}

	public void closeAlert(HashMap<String, String> mapElementParameters) {
		//WebElement alertClose = new WebDriverWait(DesiredCapabilities.driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		WebElement alertClose = findElement(mapElementParameters);
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			resultStatus.setFailureReason(resultStatus.getFailureReason() + " | locator: " + mapElementParameters.get("Locator"));
			Assert.assertTrue(false);
			return;
		}
		try {
			alertClose.click();
			takeScreenshot();
			switchToDefaultContent(null);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.CLOSE_ALERT_ERROR + " | locator: " + mapElementParameters.get("Locator"));
			Assert.assertTrue(false);
		}
	}
	
	public void acceptAlert(HashMap<String, String> mapElementParameters) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			//String text = alert.getText();
			takeScreenshot();
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.ACCEPT_ALERT_ERROR + " | locator: " + mapElementParameters.get("Locator"));
			Assert.assertTrue(false);
			return;
		}
		takeScreenshot();
	}
	
	public void dismissAlert(HashMap<String, String> mapElementParameters) {
		try {
			//WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = getDriver().switchTo().alert();
			//String text = alert.getText();
			takeScreenshot();
			alert.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.DISMISS_ALERT_ERROR + " | locator: " + mapElementParameters.get("Locator"));
			Assert.assertTrue(false);
			return;
		}
		takeScreenshot();
	}
	
	public void acceptPrompt(HashMap<String, String> mapElementParameters) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(mapElementParameters.get("Timeout")));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.sendKeys(mapElementParameters.get("Input"));
			takeScreenshot();
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.ACCEPT_PROMPT_ERROR + " | locator: " + mapElementParameters.get("Locator"));
			Assert.assertTrue(false);
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
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SCREENSHOT_ERROR);
			Assert.assertTrue(false);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.SCREENSHOT_ERROR);
			Assert.assertTrue(false);
		}
	}
}
