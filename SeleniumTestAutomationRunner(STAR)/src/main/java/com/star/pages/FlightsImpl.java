package com.star.pages;

import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
public class FlightsImpl implements Flights {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	DataFetch dataFetch = Config.context.getBean(DataFetch.class);
	WebActions webActions = Config.context.getBean(WebActions.class);

	public void selectFlight(String TCID, int itrData) {
		log.info("Perform Flight selection for TCID: {} and Iteration: {}", TCID, itrData);
		String component = "Flights";
		String element, action, tagInput, locator;
		
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
			
			if (element == "dummy") {
				//page factory cache;
			} else {
				Method method;
				try {
					method = webActions.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						//Alerts alert = Config.context.getBean(Alerts.class);
						//alert.alertClose();
						method.invoke(webActions, mapElementParameters);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
						resultStatus.setRunStatus(RunStatus.FAIL);
						resultStatus.setFailureReason(FailureReasons.FUNCTIONAL_ERROR);
						Assert.assertTrue(false);
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					resultStatus.setRunStatus(RunStatus.FAIL);
					resultStatus.setFailureReason(FailureReasons.FUNCTIONAL_ERROR);
					Assert.assertTrue(false);
				}
			}
		}
	}
	
	public void doNothing(String TCID, int itrData) {
		log.info("Did nothing in Flights section for TCID: {} | Data Iteration: {}", TCID, itrData);
		if (true) {
			resultStatus.setRunStatus(RunStatus.PASS);
		}
		log.info("Run Status: {}", resultStatus.getRunStatus());
	}
}
