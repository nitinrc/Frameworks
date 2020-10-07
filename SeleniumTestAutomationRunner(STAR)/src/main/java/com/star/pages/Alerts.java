package com.star.pages;

import com.star.core.DataFetch;
import com.star.core.WebActions;
import com.star.core.Config;

import java.util.HashMap;

public class Alerts {
	public void alertClose() {
		WebActions objWebActions = Config.context.getBean(WebActions.class);
		objWebActions.switchToDefaultContent(null);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		DataFetch objDataFetch = Config.context.getBean(DataFetch.class);
		mapElementParameters.put("Locator", objDataFetch.getMapPOM().get("Alert").get("iFrame").get("Locator"));
		mapElementParameters.put("LocatorType", objDataFetch.getMapPOM().get("Alert").get("iFrame").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", objDataFetch.getMapPOM().get("Alert").get("iFrame").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", objDataFetch.getMapPOM().get("Alert").get("iFrame").get("Timeout"));
		objWebActions.switchToFrame(mapElementParameters);
		mapElementParameters.clear();
		mapElementParameters.put("Locator", objDataFetch.getMapPOM().get("Alert").get("AlertClose").get("Locator"));
		mapElementParameters.put("LocatorType", objDataFetch.getMapPOM().get("Alert").get("AlertClose").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", objDataFetch.getMapPOM().get("Alert").get("AlertClose").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", objDataFetch.getMapPOM().get("Alert").get("AlertClose").get("Timeout"));
		objWebActions.alertClose(mapElementParameters);
	}
}
