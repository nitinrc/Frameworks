package pages;

import selenium.framework.DataFetch;
import selenium.framework.WebActions;

public class Alerts {
	public void actionAlert() {
		WebActions objWebActions = new WebActions();
		objWebActions.switchToDefaultContent();
		objWebActions.switchToFrame(DataFetch.mapPOM.get("Alert").get("iFrame").get("Locator"), Integer.parseInt(DataFetch.mapPOM.get("Alert").get("iFrame").get("Timeout")));
		objWebActions.alertElementClick(DataFetch.mapPOM.get("Alert").get("PopupClose").get("Locator"), Integer.parseInt(DataFetch.mapPOM.get("Alert").get("PopupClose").get("Timeout")));
	}
}
