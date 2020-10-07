package pages;

import java.util.HashMap;

import selenium.framework.DataFetch;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;

public class Alerts {
	public void alertClose() {
		WebActions objWebActions = FlightBookingConfig.context.getBean(WebActions.class);
		objWebActions.switchToDefaultContent(null);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
		mapElementParameters.put("Locator", objDataFetch.getPOM().get("Alert").get("iFrame").get("Locator"));
		mapElementParameters.put("LocatorType", objDataFetch.getPOM().get("Alert").get("iFrame").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", objDataFetch.getPOM().get("Alert").get("iFrame").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", objDataFetch.getPOM().get("Alert").get("iFrame").get("Timeout"));
		objWebActions.switchToFrame(mapElementParameters);
		mapElementParameters.clear();
		mapElementParameters.put("Locator", objDataFetch.getPOM().get("Alert").get("AlertClose").get("Locator"));
		mapElementParameters.put("LocatorType", objDataFetch.getPOM().get("Alert").get("AlertClose").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", objDataFetch.getPOM().get("Alert").get("AlertClose").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", objDataFetch.getPOM().get("Alert").get("AlertClose").get("Timeout"));
		objWebActions.alertClose(mapElementParameters);
	}
}
