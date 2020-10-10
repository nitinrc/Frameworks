package com.star.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

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
	void sendKeys(HashMap<String, String> mapElementParameters);
	void click(HashMap<String, String> mapElementParameters);
	String getText(HashMap<String, String> mapElementParameters);
	void scrollIntoView(HashMap<String, String> mapElementParameters);
	void dragAndDrop(HashMap<String, String> mapElementParameters);
	void switchToDefaultContent(HashMap<String, String> mapElementParameters);
	void switchToFrame(HashMap<String, String> mapElementParameters);
	void switchBrowser(HashMap<String, String> mapElementParameters);
	void closeBrowsers(HashMap<String, String> mapElementParameters);
	void alertClose(HashMap<String, String> mapElementParameters);
	void alertAccept(HashMap<String, String> mapElementParameters);
	void alertDismiss(HashMap<String, String> mapElementParameters);
	void promptAccept(HashMap<String, String> mapElementParameters);
	//void sendKeys(String... args);
}
