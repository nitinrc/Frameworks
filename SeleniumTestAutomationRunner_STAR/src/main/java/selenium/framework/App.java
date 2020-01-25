package selenium.framework;

import springBeans.FlightBookingConfig;
import springBeans.ContextRegister;
import tests.Runner;

public class App {
	private String testSuite;
	
	public String getTestSuite() {
		return this.testSuite;
	}
	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}
	public static void main(String[] args) {
		String testSuite = args[0];
		String browser = args[1];
		
		new ContextRegister();
		App objApp = FlightBookingConfig.context.getBean(App.class);
		objApp.setTestSuite(testSuite);
		objApp.getTestSuite();
		System.out.println("Test Suite: "+testSuite);
		System.out.println("Browser: "+browser);
		
		DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
		objDesiredCapabilities.setBrowser(browser);
		
		String[] args1 = {};
		Runner.main(args1);
	}
}