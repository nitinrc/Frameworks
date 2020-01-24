package pages;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pageInterfaces.Book;
import selenium.framework.DataFetch;
import selenium.framework.WebActions;
import tests.Runner;

public class BookPage implements Book {
	
	public void bookFlight(String TCID, int itrData) {
		System.out.println("Perform Booking for TCID: "+TCID+" and Iteration: "+itrData);
		String component = "Book";
		String element, action, tagInput;
		
		for (int itrSteps = 1; itrSteps <= DataFetch.mapSteps.get(component).size(); itrSteps++) {
			HashMap<String, String> mapElementParameters = new HashMap<String, String>();
			//Steps Map
			element = DataFetch.mapSteps.get(component).get(itrSteps).get("Element");
			action = DataFetch.mapSteps.get(component).get(itrSteps).get("Action");
			tagInput = DataFetch.mapSteps.get(component).get(itrSteps).get("Input");
			//POM Map
			if (element != null) {
				mapElementParameters.put("Locator", DataFetch.mapPOM.get(component).get(element).get("Locator"));
				mapElementParameters.put("LocatorType", DataFetch.mapPOM.get(component).get(element).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", DataFetch.mapPOM.get(component).get(element).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", DataFetch.mapPOM.get(component).get(element).get("Timeout"));
			}
			if (tagInput != null) {
				mapElementParameters.put("Input", DataFetch.mapData.get(TCID).get(itrData).get(tagInput));
			}
			
			ApplicationContext context1 = new AnnotationConfigApplicationContext(WebActions.class);
			WebActions objInvoke = context1.getBean(WebActions.class);
			if (element == "dummy") {
				//page factory cache;
			} else {
				Method method;
				try {
					method = objInvoke.getClass().getDeclaredMethod(action, HashMap.class);
					try {
						ApplicationContext context2 = new AnnotationConfigApplicationContext(Alerts.class);
						Alerts objAlert = context2.getBean(Alerts.class);
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
		System.out.println("Did nothing in Book section for TCID: "+TCID+" | Data Iteration: "+ itrData);
		if (1 == 1) {
			Runner.runStatus = "PASS";
		} else {
			Runner.runStatus = "FAIL";
			System.exit(0);
		}
		System.out.println("Run Status: "+Runner.runStatus);
	}
}
