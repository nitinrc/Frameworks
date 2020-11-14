package com.star.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public interface WebActions {
	WebDriver getDriver();
	void navigate(HashMap<String, String> mapElementParameters);
	String getCurrentUrl(HashMap<String, String> mapElementParameters);
	void navigateBack(HashMap<String, String> mapElementParameters);
	void navigateForward(HashMap<String, String> mapElementParameters);
	void refresh(HashMap<String, String> mapElementParameters);
	String getTitle(HashMap<String, String> mapElementParameters);
	void windowMaximize(HashMap<String, String> mapElementParameters);
	void windowFullScreen(HashMap<String, String> mapElementParameters);
	Dimension windowGetSize(HashMap<String, String> mapElementParameters);
	void windowSetSize(HashMap<String, String> mapElementParameters);
	Point windowGetPosition(HashMap<String, String> mapElementParameters);
	void windowSetPosition(HashMap<String, String> mapElementParameters);
	void findElementAndSendKeys(HashMap<String, String> mapElementParameters, String input);
	void sendKeys(WebElement element, String input);
	void findElementAndClick(HashMap<String, String> mapElementParameters);
	void click(WebElement element);
	String findElementAndGetText(HashMap<String, String> mapElementParameters);
	String getText(WebElement element);
	void findElementAndScrollIntoView(HashMap<String, String> mapElementParameters);
	void scrollIntoView(WebElement element);
	void findElementAndDragAndDrop(HashMap<String, String> mapFromElementParameters, HashMap<String, String> mapToElementParameters);
	void dragAndDrop(WebElement fromElement, WebElement toElement);
	void switchToDefaultContent(HashMap<String, String> mapElementParameters);
	void findAndSwitchToFrame(HashMap<String, String> mapElementParameters);
	void switchToFrame(WebElement iframe);
	void switchBrowser(HashMap<String, String> mapElementParameters);
	void closeBrowsers(HashMap<String, String> mapElementParameters);
	void closeAlert(HashMap<String, String> mapElementParameters);
	void acceptAlert(HashMap<String, String> mapElementParameters);
	void dismissAlert(HashMap<String, String> mapElementParameters);
	void acceptPrompt(HashMap<String, String> mapElementParameters);
	//void sendKeys(String... args);
}
