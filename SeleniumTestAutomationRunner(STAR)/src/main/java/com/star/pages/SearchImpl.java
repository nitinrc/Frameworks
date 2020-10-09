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
	Runner runner = Config.context.getBean(Runner.class);
	DataFetch dataFetch = Config.context.getBean(DataFetch.class);
	WebActions webActions = Config.context.getBean(WebActions.class);
	MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);

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
		String component = "Search";
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		mapElementParameters.put("Input", dataFetch.getMapData().get(TCID).get(itrData).get("URL"));
		webActions.navigate(mapElementParameters);

		String[] arrFields = dataFetch.getMapData().get(TCID).get(itrData).get("FieldsToValidate").split("\\|");
		HashMap<String, HashMap<String, String>> mapElements = null;
		try {
			mapElements = multiThreading.getElementIdentificationData(arrFields, component);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		} catch (ExecutionException e1) {
			e1.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
			return;
		}
		try {
			multiThreading.verifyElements(mapElements);
		} catch (InterruptedException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (ExecutionException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
	}

	public void searchFlights(String TCID, int itrData) {
		passVar = "dummy";
		log.info("Perform Flight Search for TCID: {} and Iteration: {}", TCID, itrData);
		String component = "Search";
		String element, action, tagInput, locator;

		//Alerts alert = Config.context.getBean(Alerts.class);
		//alert.alertClose();
		
		//log.info("DATA FROM LOGIN PAGE: {}", DataFetch.mapSteps);
		for (int itrSteps = 1; itrSteps <= dataFetch.getMapSteps().get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = dataFetch.getMapSteps().get(component).get(itrSteps).get("Element");
			action = dataFetch.getMapSteps().get(component).get(itrSteps).get("Action");
			tagInput = dataFetch.getMapSteps().get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				locator = dataFetch.getMapPOM().get(component).get(element).get("Locator");
				if (element.equals("CityItem")) {
					locator = locator.replace("$City", dataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
				}
				if (element.equals("DayPicker")) {
					locator = locator.replace("$Date", dataFetch.getMapData().get(TCID).get(itrData).get(tagInput+"Date"));
				}
				mapElementParameters.put("Locator", locator);
				mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get(component).get(element).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get(component).get(element).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", dataFetch.getMapPOM().get(component).get(element).get("Timeout"));
			}
			if (tagInput != null) {
				mapElementParameters.put("Input", dataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
			}
			
			if (element == "SearchBtn") {
				clickSearchBtn_CacheLookup();
			} else if (element == "ToInput") {
				enterToInput_CacheLookup(dataFetch.getMapData().get(TCID).get(itrData).get(tagInput));
			} else {
				Method method;
				try {
					//method = invoke.getClass().getDeclaredMethod(action, String.class, String.class, String.class);
					//method = invoke.getClass().getDeclaredMethod(action, String[].class);
					method = webActions.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						//Alerts alert = Config.context.getBean(Alerts.class);
						//alert.alertClose();
						//method.invoke(invoke, browser, locator, input);
						//method.invoke(invoke, new Object[] {arrArgs});
						method.invoke(webActions, mapElementParameters);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						runner.setRunStatus(RunStatus.FAIL);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						runner.setRunStatus(RunStatus.FAIL);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						runner.setRunStatus(RunStatus.FAIL);
					} catch (Exception e) {
						e.printStackTrace();
						runner.setRunStatus(RunStatus.FAIL);
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					runner.setRunStatus(RunStatus.FAIL);
				} catch (SecurityException e) {
					e.printStackTrace();
					runner.setRunStatus(RunStatus.FAIL);
				} catch (Exception e) {
					e.printStackTrace();
					runner.setRunStatus(RunStatus.FAIL);
				}
			}
		}
	}
	
	public void doNothing(String TCID, int itrData) {
		log.info("Did nothing in Search section for TCID: {} | Data Iteration: {}", TCID, itrData);
		log.info("doNothing passVar: {}", passVar);
		if (true) {
			runner.setRunStatus(RunStatus.PASS);
		}
		log.info("Run Status: {}", runner.getRunStatus());
		//SoftAssert softAssertion = new SoftAssert();
		//softAssertion.assertTrue(false);
		//Assert.assertTrue(false);
		//assertEquals(element.getText(), "Hello!");
	}

	public void openSearchEngine(String TCID, int itrData) {
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		mapElementParameters.put("Input", dataFetch.getMapData().get(TCID).get(itrData).get("URL"));
		webActions.navigate(mapElementParameters);
		log.info("Opened Search Engine in Search section for TCID: {} | Data Iteration: {}", TCID, itrData);
		if (true) {
			runner.setRunStatus(RunStatus.PASS);
		}
		log.info("Run Status: {}", runner.getRunStatus());
	}
}
