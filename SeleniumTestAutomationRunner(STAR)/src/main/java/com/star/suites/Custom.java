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
public class Custom {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);
	DataFetch dataFetch = Config.context.getBean(DataFetch.class);
	MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);
	WebActions webActions = Config.context.getBean(WebActions.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);
	SearchImpl search = Config.context.getBean(SearchImpl.class);
	FlightsImpl flights = Config.context.getBean(FlightsImpl.class);
	BookImpl book = Config.context.getBean(BookImpl.class);

	private HashMap<String, HashMap<Integer, HashMap<String, String>>> testData;

	@Test
	public void mainFlow() {
		for (Integer itrData = 1; itrData <= testData.get("MAIN_FLOW_1").size(); itrData++) {
			resultStatus.setRunStatus(RunStatus.NOT_STARTED);
			search.customSearch("MAIN_FLOW_1", itrData);
			if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
				Assert.assertTrue(false);
			}

		}
	}

	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite: Custom");
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
		log.info("@AfterSuite: Custom");
		webActions.closeBrowsers(null);
		browserConfig.getDriver().quit();
	}

	@BeforeTest
	public void beforeTest() {
		log.info("@BeforeTest: Custom");
	}

	@AfterTest
	public void afterTest() {
		log.info("@AfterTest: Custom");
		if (resultStatus.getRunStatus().equals(RunStatus.FAIL)) {
			log.error("FAILURE REASON: {}", resultStatus.getFailureReason());
		}
		Assert.assertEquals(resultStatus.getRunStatus(), RunStatus.PASS);
	}
}
