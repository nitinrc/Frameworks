package tests;

import org.testng.annotations.Test;

import pages.BookPage;
import pages.FlightsPage;
import pages.SearchPage;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.WebActions;
import springBeans.FlightBookingConfig;

import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class Smoke {
  /*@Test
  public void run() {
	  new Runner().runner(this.getClass().getName().split("\\.")[1]);
  }*/
  
  @Test
  public void SMOKE_3() {
	  int intIterations = 1;
	  SearchPage objSearch = FlightBookingConfig.context.getBean(SearchPage.class);
	  FlightsPage objFlights = FlightBookingConfig.context.getBean(FlightsPage.class);
	  BookPage objBook = FlightBookingConfig.context.getBean(BookPage.class);
	  
	  for (int itr = 1; itr <= intIterations; itr++) {
		  Runner.runStatus = "";
		  objSearch.searchFlights("SMOKE_3", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
		  
		  objFlights.selectFlight("SMOKE_3", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
		  
		  objBook.bookFlight("SMOKE_3", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
	  }
  }
  
  @Test
  public void SMOKE_4() {
	  int intIterations = 3;
	  SearchPage objSearch = FlightBookingConfig.context.getBean(SearchPage.class);
	  FlightsPage objFlights = FlightBookingConfig.context.getBean(FlightsPage.class);
	  BookPage objBook = FlightBookingConfig.context.getBean(BookPage.class);
	  
	  for (int itr = 1; itr <= intIterations; itr++) {
		  Runner.runStatus = "";
		  objSearch.searchFlights("SMOKE_4", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
		  
		  objFlights.selectFlight("SMOKE_4", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
		  
		  objFlights.doNothing("SMOKE_4", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
		  
		  objBook.bookFlight("SMOKE_4", itr);
		  if (Runner.runStatus.equals("FAIL")) {Assert.assertTrue(false);}
	  }
  }
  
  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("@BeforeSuite: Smoke");
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
	  System.out.println("@AfterSuite: Smoke");
	  WebActions objWebActions = FlightBookingConfig.context.getBean(WebActions.class);
	  objWebActions.closeBrowsers(null);
	  
	  DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
	  objDesiredCapabilities.getDriver().quit();
  }
  
  @BeforeTest
  public void beforeTest() {
	  System.out.println("@BeforeTest: Smoke");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("@AfterTest: Smoke");
  }
}
