package com.star.suites;

import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import com.star.pages.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Smoke {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	DataFetch dataFetch = Config.context.getBean(DataFetch.class);
	SearchImpl search = Config.context.getBean(SearchImpl.class);
	FlightsImpl flights = Config.context.getBean(FlightsImpl.class);
	BookImpl book = Config.context.getBean(BookImpl.class);
	MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);
	WebActions webActions = Config.context.getBean(WebActions.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);

	private HashMap<String, HashMap<Integer, HashMap<String, String>>> testData;

	@Test
	public void smokeFlow3() {
		for (int itrData = 1; itrData <= testData.get("SMOKE_3").size(); itrData++) {
			resultStatus.setRunStatus(RunStatus.IN_PROGRESS);
			search.searchFlights("SMOKE_3", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
			flights.selectFlight("SMOKE_3", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
			book.bookFlight("SMOKE_3", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
		}
	}

	@Test
	public void smokeFlow4() {
		for (int itrData = 1; itrData <= testData.get("SMOKE_4").size(); itrData++) {
			resultStatus.setRunStatus(RunStatus.IN_PROGRESS);
			search.searchFlights("SMOKE_4", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
			flights.selectFlight("SMOKE_4", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
			flights.doNothing("SMOKE_4", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}
			book.bookFlight("SMOKE_4", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			Assert.assertTrue(false);
			}
		}
	}

	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite: Smoke");
		try {
			multiThreading.dataFetch();
			testData = dataFetch.getMapData();
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
			Assert.assertTrue(false);
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
			Assert.assertTrue(false);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.TEST_SUITE_ERROR);
			Assert.assertTrue(false);
		}
	}

	@AfterSuite
	public void afterSuite() {
		log.info("@AfterSuite: Smoke");
		webActions.closeBrowsers(null);
		browserConfig.getDriver().quit();
	}

	@BeforeTest
	public void beforeTest() {
		log.info("@BeforeTest: Smoke");
	}

	@AfterTest
	public void afterTest() {
		log.info("@AfterTest: Smoke");
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			log.error("FAILURE REASON: {}", resultStatus.getFailureReason());
			Assert.assertTrue(false);
		}
		Assert.assertEquals(resultStatus.getRunStatus(), RunStatus.PASS);
	}
}
