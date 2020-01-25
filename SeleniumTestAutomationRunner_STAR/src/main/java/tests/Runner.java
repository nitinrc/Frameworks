package tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import selenium.framework.App;
import selenium.framework.DataFetch;
import springBeans.FlightBookingConfig;

public class Runner {
  public static String runStatus;
  
  public static void main(String[] args) {
	  //TestListenerAdapter objTestListenerAdapter = new TestListenerAdapter();
	  TestNG objTestNG = new TestNG();
	  /*List<String> suites = Lists.newArrayList();
	  suites.add("testng.xml");
	  objTestNG.setTestSuites(suites);*/
	  App objApp = FlightBookingConfig.context.getBean(App.class);
	  if (objApp.getTestSuite().equals("Regression")) {
		  objTestNG.setTestClasses(new Class[] {Regression.class});
	  } else if (objApp.getTestSuite().equals("Smoke")) {
		  objTestNG.setTestClasses(new Class[] {Smoke.class});
	  }
	  //objTestNG.addListener(objTestListenerAdapter);
	  objTestNG.run();
  }
  
  public void runner(String coverage) {
	  System.out.println("Running Test Suite: "+coverage);
	  boolean flagCoverage = false;
	  String TCID = "";
	  int intDataIterations = 0;
	  DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
	  //Iterate Rows
	  Iterator hmIterator1 = objDataFetch.getTestCases().entrySet().iterator();
	  while (hmIterator1.hasNext()) {
	      Map.Entry mapElement1 = (Map.Entry)hmIterator1.next();
	      TCID = (String) mapElement1.getKey();
	      //Iterates Test Data iterations
	      intDataIterations = objDataFetch.getData().get(TCID).size();
	      for (int itrData = 1; itrData <= intDataIterations; itrData++) {
	    	  runStatus = "";
	    	  HashMap<String, String> mapTCData = objDataFetch.getTestCases().get(mapElement1.getKey());
		      //System.out.println("mapTCData: "+mapTCData);
		      flagCoverage = false;
			  for (String itemCoverage : mapTCData.get("Coverage").split(":")) {
				  if (itemCoverage.toLowerCase().equals(coverage.toLowerCase())) {
					  flagCoverage = true;
					  break;
				  }
			  }
			  //Iterate Columns
			  Iterator hmIterator2 = mapTCData.entrySet().iterator();
			  while (hmIterator2.hasNext()) {
		          Map.Entry mapElement2 = (Map.Entry)hmIterator2.next();
		          String val = (String) mapElement2.getValue(); 
		          //System.out.println(mapElement2.getKey() + " : " + val);
		          if ((!(mapElement2.getKey().equals("Coverage"))) & flagCoverage) {
		        	  try {
						Class<?> refClass = Class.forName("pages."+val.split("\\.")[0]);
						Method method = null;
						try {
							method = refClass.newInstance().getClass().getMethod(val.split("\\.")[1], String.class, int.class);
						} catch (NoSuchMethodException e) {e.printStackTrace();} catch (SecurityException e) {e.printStackTrace();} catch (InstantiationException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
						try {
							method.invoke(refClass.newInstance(), TCID, itrData);
							if (Runner.runStatus.equals("FAIL")) {
								break;
							}
						} catch (IllegalAccessException e) {e.printStackTrace();} catch (IllegalArgumentException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();} catch (InstantiationException e) {e.printStackTrace();}
					  } catch (ClassNotFoundException e) {e.printStackTrace();}
		          }
			  }
	      }
	  }
  }
}