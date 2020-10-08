package com.star.suites;

import com.star.Runner;
import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.star.pages.*;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Smoke {
  @Test
  public void SMOKE_3() {
	  int intIterations = 1;
	  Runner runner = Config.context.getBean(Runner.class);
	  SearchImpl search = Config.context.getBean(SearchImpl.class);
	  FlightsImpl flights = Config.context.getBean(FlightsImpl.class);
	  BookImpl book = Config.context.getBean(BookImpl.class);
	  
	  for (int itr = 1; itr <= intIterations; itr++) {
		  runner.setRunStatus(RunStatus.NOT_STARTED);
		  search.searchFlights("SMOKE_3", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
		  flights.selectFlight("SMOKE_3", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
		  book.bookFlight("SMOKE_3", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
	  }
  }
  
  @Test
  public void SMOKE_4() {
	  int intIterations = 3;
	  Runner runner = Config.context.getBean(Runner.class);
	  SearchImpl search = Config.context.getBean(SearchImpl.class);
	  FlightsImpl flights = Config.context.getBean(FlightsImpl.class);
	  Book book = Config.context.getBean(Book.class);
	  
	  for (int itr = 1; itr <= intIterations; itr++) {
		  runner.setRunStatus(RunStatus.NOT_STARTED);
		  search.searchFlights("SMOKE_4", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
		  flights.selectFlight("SMOKE_4", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
		  flights.doNothing("SMOKE_4", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
		  book.bookFlight("SMOKE_4", itr);
		  if (runner.getRunStatus().equals(RunStatus.FAIL)) {
		  	Assert.assertTrue(false);
		  }
	  }
  }
  
  @BeforeSuite
  public void beforeSuite() {
	  log.info("@BeforeSuite: Smoke");
	  MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);
	  try {
		  multiThreading.dataFetch();
	  } catch (InterruptedException e) {
	  	e.printStackTrace();
	  } catch (ExecutionException e) {
	  	e.printStackTrace();}
	}
  
  @AfterSuite
  public void afterSuite() {
	  log.info("@AfterSuite: Smoke");
	  WebActions webActions = Config.context.getBean(WebActions.class);
	  webActions.closeBrowsers(null);
	  
	  BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
	  browserConfig.getDriver().quit();
  }
  
  @BeforeTest
  public void beforeTest() {
  	log.info("@BeforeTest: Smoke");
  }

  @AfterTest
  public void afterTest() {
  	log.info("@AfterTest: Smoke");
  }
}
