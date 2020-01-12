package tests;

import org.testng.annotations.Test;

import pages.BookPage;
import pages.FlightsPage;
import pages.SearchPage;
import selenium.framework.App;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.WebActions;

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
	  SearchPage objSearch = new SearchPage();
	  FlightsPage objFlights = new FlightsPage();
	  BookPage objBook = new BookPage();
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
	  SearchPage objSearch = new SearchPage();
	  FlightsPage objFlights = new FlightsPage();
	  BookPage objBook = new BookPage();
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
	  DataFetch objData = new DataFetch();
	  objData.getTestCases();
	  objData.getSteps();
	  objData.getPOM();
	  objData.getData();
	  DesiredCapabilities objDC = new DesiredCapabilities();
	  DesiredCapabilities.browser = App.browser;
	  objDC.getDriver(DesiredCapabilities.browser);
  }
  
  @AfterSuite
  public void afterSuite() {
	  System.out.println("@AfterSuite: Smoke");
	  new WebActions().closeBrowsers(null);
	  DesiredCapabilities.driver.quit();
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
