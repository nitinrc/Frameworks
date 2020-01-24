package tests;

import org.testng.annotations.Test;

import pages.Alerts;
import selenium.framework.App;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.WebActions;

import org.testng.annotations.BeforeTest;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class Regression {
  @Test
  public void run() {
	  ApplicationContext context = new AnnotationConfigApplicationContext(Runner.class);
	  Runner objRunner = context.getBean(Runner.class);
	  objRunner.runner(this.getClass().getName().split("\\.")[1]);
  }
  
  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("@BeforeSuite: Regression");
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
	  System.out.println("@AfterSuite: Regression");
	  ApplicationContext context = new AnnotationConfigApplicationContext(WebActions.class);
	  WebActions objWebActions = context.getBean(WebActions.class);
	  objWebActions.closeBrowsers(null);
	  DesiredCapabilities.driver.quit();
  }
  
  @BeforeTest
  public void beforeTest() {
	  System.out.println("@BeforeTest: Regression");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("@AfterTest: Regression");
  }
}
