package com.star.pages;

import com.star.core.DataFetch;
import com.star.core.WebActions;
import com.star.core.Config;

import java.util.HashMap;

public class Alerts {
	public void alertClose() {
		WebActions webActions = Config.context.getBean(WebActions.class);
		webActions.switchToDefaultContent(null);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		DataFetch dataFetch = Config.context.getBean(DataFetch.class);
		mapElementParameters.put("Locator", dataFetch.getMapPOM().get("Alert").get("iFrame").get("Locator"));
		mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get("Alert").get("iFrame").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get("Alert").get("iFrame").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", dataFetch.getMapPOM().get("Alert").get("iFrame").get("Timeout"));
		webActions.switchToFrame(mapElementParameters);
		mapElementParameters.clear();
		mapElementParameters.put("Locator", dataFetch.getMapPOM().get("Alert").get("AlertClose").get("Locator"));
		mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get("Alert").get("AlertClose").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get("Alert").get("AlertClose").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", dataFetch.getMapPOM().get("Alert").get("AlertClose").get("Timeout"));
		webActions.alertClose(mapElementParameters);
	}
}
