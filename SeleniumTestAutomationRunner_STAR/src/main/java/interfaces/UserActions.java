package interfaces;

import java.util.HashMap;

public interface UserActions {
	public void navigate(HashMap<String, String> mapElementParameters);
	public void getCurrentUrl(HashMap<String, String> mapElementParameters);
	public void navigateBack(HashMap<String, String> mapElementParameters);
	public void navigateForward(HashMap<String, String> mapElementParameters);
	public void refresh(HashMap<String, String> mapElementParameters);
	public void getTitle(HashMap<String, String> mapElementParameters);
	public void windowMaximize(HashMap<String, String> mapElementParameters);
	public void windowFullScreen(HashMap<String, String> mapElementParameters);
	public void windowGetSize(HashMap<String, String> mapElementParameters);
	public void windowSetSize(HashMap<String, String> mapElementParameters);
	public void windowGetPosition(HashMap<String, String> mapElementParameters);
	public void windowSetPosition(HashMap<String, String> mapElementParameters);
	public void sendKeys(HashMap<String, String> mapElementParameters);
	public void click(HashMap<String, String> mapElementParameters);
	public void getText(HashMap<String, String> mapElementParameters);
	public void scrollIntoView(HashMap<String, String> mapElementParameters);
	public void dragAndDrop(HashMap<String, String> mapElementParameters);
	public void switchToDefaultContent();
	public void switchToFrame(String xpath, int timeout);
	public void switchBrowser(HashMap<String, String> mapElementParameters);
	public void closeBrowsers(HashMap<String, String> mapElementParameters);
	public void alertElementClick(String locator, int timeout);
	public void alertAccept(HashMap<String, String> mapElementParameters);
	public void alertDismiss(HashMap<String, String> mapElementParameters);
	public void promptAccept(HashMap<String, String> mapElementParameters);
	//public void sendKeys(String... args);
}
