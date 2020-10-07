package springBeans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
import pages.Alerts;
import selenium.framework.App;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.FindElement;
import selenium.framework.ReadPropertyFile;
import selenium.framework.WebActions;
import tests.Runner;
*/
@Configuration
public class FlightBookingConfig {
	public static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
	/*@Bean
	public Runner runner() {
		return new Runner();
	}
	@Bean
	public DataFetch dataFetch() {
		return new DataFetch();
	}
	@Bean
	public DesiredCapabilities desiredCapabilities() {
		return new DesiredCapabilities();
	}
	@Bean
	public FindElement findElement() {
		return new FindElement();
	}
	@Bean
	public ReadPropertyFile readPropertyFile() {
		return new ReadPropertyFile();
	}
	@Bean
	public WebActions webActions() {
		return new WebActions();
	}
	@Bean
	public Alerts alerts() {
		return new Alerts();
	}*/
}