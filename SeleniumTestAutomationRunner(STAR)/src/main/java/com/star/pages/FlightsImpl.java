package com.star.pages;

import com.star.Runner;
import com.star.core.Config;
import com.star.core.DataFetch;
import com.star.core.RunStatus;
import com.star.core.WebActions;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
public class FlightsImpl implements Flights {

	public void selectFlight(String TCID, int itrData) {
		log.info("Perform Flight selection for TCID: {} and Iteration: {}", TCID, itrData);
		String component = "Flights";
		String element, action, tagInput, locator;
		Runner runner = Config.context.getBean(Runner.class);
		DataFetch dataFetch = Config.context.getBean(DataFetch.class);
		
		for (int itrSteps = 1; itrSteps <= dataFetch.getMapSteps().get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = dataFetch.getMapSteps().get(component).get(itrSteps).get("Element");
			action = dataFetch.getMapSteps().get(component).get(itrSteps).get("Action");
			tagInput = dataFetch.getMapSteps().get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				locator = dataFetch.getMapPOM().get(component).get(element).get("Locator");
				if (element.equals("SelectFlight")) {
					locator = locator.replace("$Time", dataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
				}
				mapElementParameters.put("Locator", locator);
				mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get(component).get(element).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get(component).get(element).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", dataFetch.getMapPOM().get(component).get(element).get("Timeout"));
			}
			if (tagInput != null) {
				mapElementParameters.put("Input", dataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
			}
			
			WebActions invoke = Config.context.getBean(WebActions.class);
			if (element == "dummy") {
				//page factory cache;
			} else {
				Method method;
				try {
					method = invoke.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						//Alerts alert = FlightBookingConfig.context.getBean(Alerts.class);
						//alert.alertClose();
						method.invoke(invoke, mapElementParameters);
						if (runner.getRunStatus().equals(RunStatus.FAIL)) {
							System.exit(0);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void doNothing(String TCID, int itrData) {
		log.info("Did nothing in Flights section for TCID: {} | Data Iteration: {}", TCID, itrData);
		Runner runner = Config.context.getBean(Runner.class);
		if (true) {
			runner.setRunStatus(RunStatus.PASS);
		}
		log.info("Run Status: {}", runner.getRunStatus());
	}
}
