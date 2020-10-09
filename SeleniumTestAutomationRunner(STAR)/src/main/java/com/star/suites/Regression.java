package com.star.suites;

import com.star.Runner;
import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Regression {
	Runner runner = Config.context.getBean(Runner.class);
	Driver driver = Config.context.getBean(Driver.class);
	MultiThreading multiThreading = Config.context.getBean(MultiThreading.class);
	WebActions webActions = Config.context.getBean(WebActions.class);
	BrowserConfig browserConfig = Config.context.getBean(BrowserConfig.class);

	@Test
	public void run() {
		driver.testsRunner(this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]);
	}
  
	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite: Regression");
		try {
			multiThreading.dataFetch();
		} catch (InterruptedException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (ExecutionException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
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
	}
}
