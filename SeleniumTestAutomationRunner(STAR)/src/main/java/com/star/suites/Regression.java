package com.star.suites;

import com.star.core.*;
import com.star.pages.BookImpl;
import com.star.pages.FlightsImpl;
import com.star.pages.SearchImpl;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Regression {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	Driver driver = Config.context.getBean(Driver.class);
	DataFetch dataFetch = Config.context.getBean(DataFetch.class);
	MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);
	WebActions webActions = Config.context.getBean(WebActions.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
	SearchImpl search = Config.context.getBean(SearchImpl.class);
	FlightsImpl flights = Config.context.getBean(FlightsImpl.class);
	BookImpl book = Config.context.getBean(BookImpl.class);

	private HashMap<String, HashMap<Integer, HashMap<String, String>>> testData;

	@Test
	public void run() {
		driver.testsRunner(this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]);
	}
  
	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite: Regression");
		try {
			multiThreading.dataFetch();
			testData = dataFetch.getMapData();
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
		}
	}

	@AfterSuite
	public void afterSuite() {
		log.info("@AfterSuite: Regression");
		webActions.closeBrowsers(null);
		browserConfig.getDriver().quit();
	}

	@BeforeTest
	public void beforeTest() {
		log.info("@BeforeTest: Regression");
	}

	@AfterTest
	public void afterTest() {
		log.info("@AfterTest: Regression");
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			log.error("FAILURE REASON: {}", resultStatus.getFailureReason());
		}
		Assert.assertEquals(resultStatus.getRunStatus(), RunStatus.PASS);
	}
}
