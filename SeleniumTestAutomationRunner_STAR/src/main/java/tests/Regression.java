package tests;

import org.testng.annotations.Test;

import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;

import org.testng.annotations.BeforeTest;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class Regression {
  @Test
  public void run() {
	  Runner objRunner = FlightBookingConfig.context.getBean(Runner.class);
	  objRunner.runner(this.getClass().getName().split("\\.")[1]);
  }
  
  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("@BeforeSuite: Regression");
	  DataFetch objData = FlightBookingConfig.context.getBean(DataFetch.class);
	  objData.setTestCases();
	  objData.setSteps();
	  objData.setPOM();
	  objData.setData();
	  
	  DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
	  objDesiredCapabilities.setDriver();
  }
  
  @AfterSuite
  public void afterSuite() {
	  System.out.println("@AfterSuite: Regression");
	  WebActions objWebActions = FlightBookingConfig.context.getBean(WebActions.class);
	  objWebActions.closeBrowsers(null);
	  
	  DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
	  objDesiredCapabilities.getDriver().quit();
  }
  
  @BeforeTest
  public void beforeTest() {
	  System.out.println("@BeforeTest: Regression");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("@AfterTest: Regression");
  }
}
