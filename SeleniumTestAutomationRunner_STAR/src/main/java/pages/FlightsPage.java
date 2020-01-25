package pages;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import pageInterfaces.Flights;
import selenium.framework.DataFetch;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;
import tests.Runner;

public class FlightsPage implements Flights {

	public void selectFlight(String TCID, int itrData) {
		System.out.println("Perform Flight selection for TCID: "+TCID+" and Iteration: "+itrData);
		String component = "Flights";
		String element, action, tagInput, locator;
		DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
		
		for (int itrSteps = 1; itrSteps <= objDataFetch.getSteps().get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = objDataFetch.getSteps().get(component).get(itrSteps).get("Element");
			action = objDataFetch.getSteps().get(component).get(itrSteps).get("Action");
			tagInput = objDataFetch.getSteps().get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				locator = objDataFetch.getPOM().get(component).get(element).get("Locator");
				if (element.equals("SelectFlight")) {
					locator = locator.replace("$Time", objDataFetch.getData().get(TCID).get(itrData).get(tagInput));
				}
				mapElementParameters.put("Locator", locator);
				mapElementParameters.put("LocatorType", objDataFetch.getPOM().get(component).get(element).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", objDataFetch.getPOM().get(component).get(element).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", objDataFetch.getPOM().get(component).get(element).get("Timeout"));
			}
			if (tagInput != null) {
				mapElementParameters.put("Input", objDataFetch.getData().get(TCID).get(itrData).get(tagInput));
			}
			
			WebActions objInvoke = FlightBookingConfig.context.getBean(WebActions.class);
			if (element == "dummy") {
				//page factory cache;
			} else {
				Method method;
				try {
					method = objInvoke.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						Alerts objAlert = FlightBookingConfig.context.getBean(Alerts.class);
						objAlert.alertClose();
						method.invoke(objInvoke, mapElementParameters);
						if (Runner.runStatus.equals("FAIL")) {
							System.exit(0);
						}
					} catch (IllegalAccessException e) {e.printStackTrace();} catch (IllegalArgumentException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {e.printStackTrace();} catch (SecurityException e) {e.printStackTrace();}
			}
		}
	}
	
	public void doNothing(String TCID, int itrData) {
		System.out.println("Did nothing in Flights section for TCID: "+TCID+" | Data Iteration: "+ itrData);
		if (1 == 1) {
			Runner.runStatus = "PASS";
		} else {
			Runner.runStatus = "FAIL";
			System.exit(0);
		}
		System.out.println("Run Status: "+Runner.runStatus);
	}
}
