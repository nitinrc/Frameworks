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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
	  
	  ApplicationContext context1 = new AnnotationConfigApplicationContext(SearchPage.class);
	  SearchPage objSearch = context1.getBean(SearchPage.class);
	  
	  ApplicationContext context2 = new AnnotationConfigApplicationContext(FlightsPage.class);
	  FlightsPage objFlights = context2.getBean(FlightsPage.class);
	  
	  ApplicationContext context3 = new AnnotationConfigApplicationContext(BookPage.class);
	  BookPage objBook = context3.getBean(BookPage.class);
	  
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
	  
	  ApplicationContext context1 = new AnnotationConfigApplicationContext(SearchPage.class);
	  SearchPage objSearch = context1.getBean(SearchPage.class);
	  
	  ApplicationContext context2 = new AnnotationConfigApplicationContext(FlightsPage.class);
	  FlightsPage objFlights = context2.getBean(FlightsPage.class);
	  
	  ApplicationContext context3 = new AnnotationConfigApplicationContext(BookPage.class);
	  BookPage objBook = context3.getBean(BookPage.class);
	  
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
	  ApplicationContext context1 = new AnnotationConfigApplicationContext(DataFetch.class);
	  DataFetch objData = context1.getBean(DataFetch.class);
	  objData.getTestCases();
	  objData.getSteps();
	  objData.getPOM();
	  objData.getData();
	  
	  ApplicationContext context2 = new AnnotationConfigApplicationContext(DesiredCapabilities.class);
	  DesiredCapabilities objDesiredCapabilities = context2.getBean(DesiredCapabilities.class);
	  DesiredCapabilities.browser = App.browser;
	  objDesiredCapabilities.getDriver(DesiredCapabilities.browser);
  }
  
  @AfterSuite
  public void afterSuite() {
	  System.out.println("@AfterSuite: Smoke");
	  ApplicationContext context = new AnnotationConfigApplicationContext(WebActions.class);
	  WebActions objWebActions = context.getBean(WebActions.class);
	  objWebActions.closeBrowsers(null);
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
