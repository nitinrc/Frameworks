package com.star.pages;

import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.star.Runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SearchImpl implements Search {
	public static String passVar;
	//PageFactory.initElements(DesiredCapabilities.driver, SearchPage);
	//PageFactory.initElements(DesiredCapabilities.driver, this);
	
	//@FindBy(xpath = "//a[text()=\"Search\"]")
	@FindBy(how = How.XPATH, using = "//a[text()=\"Search\"]")
	@CacheLookup
	private WebElement btnSearch;
	private void clickSearchBtn_CacheLookup() {
		btnSearch.click();
	}
	
	@FindBy(how = How.XPATH, using = "//input[@placeholder=\"To\"]")
	@CacheLookup
	private WebElement inputTo;
	private void enterToInput_CacheLookup(String text) {
		inputTo.click();
		inputTo.clear();
		inputTo.sendKeys(text);
	}
	
	public void validateSearchPageElements(String TCID, int itrData) {
		DataFetch objDataFetch = Config.context.getBean(DataFetch.class);
		String component = "Search";
		WebActions objWebActions = Config.context.getBean(WebActions.class);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		mapElementParameters.put("Input", objDataFetch.getMapData().get(TCID).get(itrData).get("URL"));
		objWebActions.navigate(mapElementParameters);
		
		MultiThreading objMultiThreading = Config.context.getBean(MultiThreading.class);
		String[] arrFields = objDataFetch.getMapData().get(TCID).get(itrData).get("FieldsToValidate").split("\\|");
		HashMap<String, HashMap<String, String>> mapElements = null;
		try {
			mapElements = objMultiThreading.getElementIdentificationData(arrFields, component);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		try {
			objMultiThreading.verifyElements(mapElements);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void searchFlights(String TCID, int itrData) {
		passVar = "dummy";
		log.info("Perform Flight Search for TCID: {} and Iteration: {}", TCID, itrData);
		String component = "Search";
		String element, action, tagInput, locator;
		Runner objRunner = Config.context.getBean(Runner.class);
		DataFetch objDataFetch = Config.context.getBean(DataFetch.class);
		
		//log.info("DATA FROM LOGIN PAGE: {}", DataFetch.mapSteps);
		for (int itrSteps = 1; itrSteps <= objDataFetch.getMapSteps().get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = objDataFetch.getMapSteps().get(component).get(itrSteps).get("Element");
			action = objDataFetch.getMapSteps().get(component).get(itrSteps).get("Action");
			tagInput = objDataFetch.getMapSteps().get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				locator = objDataFetch.getMapPOM().get(component).get(element).get("Locator");
				if (element.equals("CityItem")) {
					locator = locator.replace("$City", objDataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
				}
				if (element.equals("DayPicker")) {
					locator = locator.replace("$Date", objDataFetch.getMapData().get(TCID).get(itrData).get(tagInput+"Date"));
				}
				mapElementParameters.put("Locator", locator);
				mapElementParameters.put("LocatorType", objDataFetch.getMapPOM().get(component).get(element).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", objDataFetch.getMapPOM().get(component).get(element).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", objDataFetch.getMapPOM().get(component).get(element).get("Timeout"));
			}
			if (tagInput != null) {
				mapElementParameters.put("Input", objDataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
			}
			
			WebActions objInvoke = Config.context.getBean(WebActions.class);
			if (element == "SearchBtn") {
				clickSearchBtn_CacheLookup();
			} else if (element == "ToInput") {
				enterToInput_CacheLookup(objDataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
			} else {
				Method method;
				try {
					//method = objInvoke.getClass().getDeclaredMethod(action, String.class, String.class, String.class);
					//method = objInvoke.getClass().getDeclaredMethod(action, String[].class);
					method = objInvoke.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						//Alerts objAlert = FlightBookingConfig.context.getBean(Alerts.class);
						//objAlert.alertClose();
						//method.invoke(objInvoke, browser, locator, input);
						//method.invoke(objInvoke, new Object[] {arrArgs});//new Object[] {new String[] {"a", "s", "d"}}
						method.invoke(objInvoke, mapElementParameters);
						if (objRunner.getRunStatus().equals("FAIL")) {
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
		log.info("Did nothing in Search section for TCID: {} | Data Iteration: {}", TCID, itrData);
		log.info("doNothing passVar: {}", passVar);
		Runner objRunner = Config.context.getBean(Runner.class);
		if (true) {
			objRunner.setRunStatus(RunStatus.PASS);
		}
		log.info("Run Status: {}", objRunner.getRunStatus());
		//SoftAssert softAssertion = new SoftAssert();
		//softAssertion.assertTrue(false);
		//Assert.assertTrue(false);
		//assertEquals(element.getText(), "Hello!");
	}
}
