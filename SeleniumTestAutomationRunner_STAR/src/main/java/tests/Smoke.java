package tests;

import org.testng.annotations.Test;

import selenium.framework.App;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.WebActions;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class Smoke {
  @Test
  public void run() {
	  new Runner().runner(this.getClass().getName().split("\\.")[1]);
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
	  new WebActions().closeBrowsers(0);
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
