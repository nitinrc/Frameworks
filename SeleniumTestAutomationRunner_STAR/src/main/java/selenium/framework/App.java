package selenium.framework;

import springBeans.FlightBookingConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import springBeans.ContextRegister;
import tests.Runner;

@Component("App")
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
		new AnnotationConfigApplicationContext(ContextRegister.class);
		App objApp = FlightBookingConfig.context.getBean(App.class);
		objApp.setTestSuite(testSuite);
		objApp.getTestSuite();
		System.out.println("Test Suite: "+testSuite);
		System.out.println("Browser: "+browser);
		DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
		objDesiredCapabilities.setBrowser(browser);
		
		/* import org.springframework.context.ApplicationContext;
		 * ApplicationContext context = new AnnotationConfigApplicationContext(DesiredCapabilities.class);
		 * Above requires @Bean to be defined in @Configuration, and does not require refresh and register of class
		 * context.refresh();
		 * context.register(DesiredCapabilities.class);
		 */
		
		String[] args1 = {};
		Runner.main(args1);
	}
}