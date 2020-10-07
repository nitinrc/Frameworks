package tests;

import org.testng.annotations.Test;

import selenium.framework.DesiredCapabilities;
import selenium.framework.MultiThreadingExecutorService;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;

import org.testng.annotations.BeforeTest;

import java.util.concurrent.ExecutionException;

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
	  MultiThreadingExecutorService objMultiThreadingExecutorService = FlightBookingConfig.context.getBean(MultiThreadingExecutorService.class);
	  try {
		  objMultiThreadingExecutorService.dataFetch();
	  } catch (InterruptedException e) {e.printStackTrace();
	  } catch (ExecutionException e) {e.printStackTrace();}
	  
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
