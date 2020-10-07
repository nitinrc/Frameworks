package pages;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import pageInterfaces.Search;
import selenium.framework.DataFetch;
import selenium.framework.MultiThreadingExecutorService;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;
import tests.Runner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchPage implements Search {
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
		DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
		String component = "Search";
		WebActions objWebActions = FlightBookingConfig.context.getBean(WebActions.class);
		HashMap<String, String> mapElementParameters = new HashMap<String, String>();
		mapElementParameters.put("Input", "http://makemytrip.com");
		objWebActions.navigate(mapElementParameters);
		
		MultiThreadingExecutorService objMultiThreadingExecutorService = FlightBookingConfig.context.getBean(MultiThreadingExecutorService.class);
		String[] arrFields = objDataFetch.getData().get(TCID).get(itrData).get("FieldsToValidate").split("\\|");
		HashMap<String, HashMap<String, String>> mapElements = null;
		try {
			mapElements = objMultiThreadingExecutorService.getElementIdentificationData(arrFields, component);
		} catch (InterruptedException e1) {e1.printStackTrace();
		} catch (ExecutionException e1) {e1.printStackTrace();}
		try {
			objMultiThreadingExecutorService.verifyElements(mapElements);
		} catch (InterruptedException e) {e.printStackTrace();
		} catch (ExecutionException e) {e.printStackTrace();}
	}

	public void searchFlights(String TCID, int itrData) {
		passVar = "dummy";
		System.out.println("Perform Flight Search for TCID: "+TCID+" and Iteration: "+itrData);
		String component = "Search";
		String element, action, tagInput, locator;
		Runner objRunner = FlightBookingConfig.context.getBean(Runner.class);
		DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
		
		//System.out.println("DATA FROM LOGIN PAGE: "+DataFetch.mapSteps);
		for (int itrSteps = 1; itrSteps <= objDataFetch.getSteps().get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = objDataFetch.getSteps().get(component).get(itrSteps).get("Element");
			action = objDataFetch.getSteps().get(component).get(itrSteps).get("Action");
			tagInput = objDataFetch.getSteps().get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				locator = objDataFetch.getPOM().get(component).get(element).get("Locator");
				if (element.equals("CityItem")) {
					locator = locator.replace("$City", objDataFetch.getData().get(TCID).get(itrData).get(tagInput));
				}
				if (element.equals("DayPicker")) {
					locator = locator.replace("$Date", objDataFetch.getData().get(TCID).get(itrData).get(tagInput+"Date"));
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
			if (element == "SearchBtn") {
				clickSearchBtn_CacheLookup();
			} else if (element == "ToInput") {
				enterToInput_CacheLookup(objDataFetch.getData().get(TCID).get(itrData).get(tagInput));
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
					} catch (IllegalAccessException e) {e.printStackTrace();} catch (IllegalArgumentException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {e.printStackTrace();} catch (SecurityException e) {e.printStackTrace();}
			}
		}
	}
	
	public void doNothing(String TCID, int itrData) {
		System.out.println("Did nothing in Search section for TCID: "+TCID+" | Data Iteration: "+ itrData);
		System.out.println("doNothing passVar: "+passVar);
		Runner objRunner = FlightBookingConfig.context.getBean(Runner.class);
		if (true) {
			objRunner.setRunStatus("PASS");
		}
		System.out.println("Run Status: "+objRunner.getRunStatus());
		//SoftAssert softAssertion = new SoftAssert();
		//softAssertion.assertTrue(false);
		//Assert.assertTrue(false);
		//assertEquals(element.getText(), "Hello!");
	}
}
