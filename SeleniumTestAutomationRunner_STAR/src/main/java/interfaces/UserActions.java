package interfaces;

import java.util.HashMap;

public interface UserActions {
	public void navigate(HashMap<String, String> mapElementParameters);
	public void getCurrentUrl();
	public void navigateBack();
	public void navigateForward();
	public void refresh();
	public void getTitle();
	public void windowMaximize();
	public void windowFullScreen();
	public void windowGetSize();
	public void windowSetSize(HashMap<String, String> mapElementParameters);
	public void windowGetPosition();
	public void windowSetPosition(HashMap<String, String> mapElementParameters);
	public void sendKeys(HashMap<String, String> mapElementParameters);
	public void click(HashMap<String, String> mapElementParameters);
	public void getText(HashMap<String, String> mapElementParameters);
	public void scrollIntoView(HashMap<String, String> mapElementParameters);
	public void dragAndDrop(HashMap<String, String> mapElementParameters);
	public void switchToDefaultContent();
	public void switchToFrame(String frameName);
	public void switchBrowser();
	public void closeBrowsers(int keepOpen);
	public void alertAccept(HashMap<String, String> mapElementParameters);
	public void alertDismiss(HashMap<String, String> mapElementParameters);
	public void promptAccept(HashMap<String, String> mapElementParameters);
	//public void sendKeys(String... args);
}
