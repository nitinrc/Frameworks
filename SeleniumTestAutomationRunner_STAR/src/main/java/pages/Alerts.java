package pages;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import selenium.framework.DataFetch;
import selenium.framework.WebActions;

public class Alerts {
	public void alertClose() {
		ApplicationContext context = new AnnotationConfigApplicationContext(WebActions.class);
		WebActions objWebActions = context.getBean(WebActions.class);
		objWebActions.switchToDefaultContent(null);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		mapElementParameters.put("Locator", DataFetch.mapPOM.get("Alert").get("iFrame").get("Locator"));
		mapElementParameters.put("LocatorType", DataFetch.mapPOM.get("Alert").get("iFrame").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", DataFetch.mapPOM.get("Alert").get("iFrame").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", DataFetch.mapPOM.get("Alert").get("iFrame").get("Timeout"));
		objWebActions.switchToFrame(mapElementParameters);
		mapElementParameters.clear();
		mapElementParameters.put("Locator", DataFetch.mapPOM.get("Alert").get("AlertClose").get("Locator"));
		mapElementParameters.put("LocatorType", DataFetch.mapPOM.get("Alert").get("AlertClose").get("LocatorType"));
		mapElementParameters.put("ExpectedCondition", DataFetch.mapPOM.get("Alert").get("AlertClose").get("ExpectedCondition"));
		mapElementParameters.put("Timeout", DataFetch.mapPOM.get("Alert").get("AlertClose").get("Timeout"));
		objWebActions.alertClose(mapElementParameters);
	}
}
