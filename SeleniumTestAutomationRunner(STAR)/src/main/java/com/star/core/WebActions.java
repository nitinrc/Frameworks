package com.star.core;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public interface WebActions {
	WebDriver getDriver();
	void navigate(HashMap<String, String> mapElementParameters);
	void getCurrentUrl(HashMap<String, String> mapElementParameters);
	void navigateBack(HashMap<String, String> mapElementParameters);
	void navigateForward(HashMap<String, String> mapElementParameters);
	void refresh(HashMap<String, String> mapElementParameters);
	void getTitle(HashMap<String, String> mapElementParameters);
	void windowMaximize(HashMap<String, String> mapElementParameters);
	void windowFullScreen(HashMap<String, String> mapElementParameters);
	void windowGetSize(HashMap<String, String> mapElementParameters);
	void windowSetSize(HashMap<String, String> mapElementParameters);
	void windowGetPosition(HashMap<String, String> mapElementParameters);
	void windowSetPosition(HashMap<String, String> mapElementParameters);
	void sendKeys(HashMap<String, String> mapElementParameters);
	void click(HashMap<String, String> mapElementParameters);
	void getText(HashMap<String, String> mapElementParameters);
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
