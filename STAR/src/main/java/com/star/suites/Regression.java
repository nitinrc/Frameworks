package com.star.suites;

import com.star.core.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Regression {
	@Test
	public void run() {
		Driver driver = Config.context.getBean(Driver.class);
		driver.testsRunner(this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]);
	}
  
	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite: Regression");
		MultiThreading objMultiThreading = Config.context.getBean(MultiThreading.class);
		try {
			objMultiThreading.dataFetch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void afterSuite() {
		log.info("@AfterSuite: Regression");
		WebActions objWebActions = Config.context.getBean(WebActions.class);
		objWebActions.closeBrowsers(null);

		BrowserConfig objBrowserConfig = Config.context.getBean(BrowserConfig.class);
		objBrowserConfig.getDriver().quit();
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
