package selenium.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import springBeans.FlightBookingConfig;

public class DesiredCapabilities {
	private WebDriver driver;
	private String browser;
	
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver() {
		WebDriver driver = null;
		DesiredCapabilities objDesiredCapabilities = FlightBookingConfig.context.getBean(DesiredCapabilities.class);
		if (objDesiredCapabilities.getBrowser().toLowerCase().equals("chrome")) {
			driver = getChromeDriver();
		} else if (objDesiredCapabilities.getBrowser().toLowerCase().equals("firefox")) {
			//driver = (FirefoxDriver) getFirefoxDriver();
		} else if (objDesiredCapabilities.getBrowser().toLowerCase().equals("ie")) {
			//driver = (InternetExplorerDriver) getInternetExplorerDriver();
		} else if (objDesiredCapabilities.getBrowser().toLowerCase().equals("htmlunit")) {
			driver = getHTMLUnitDriver();
		}
		this.driver = driver;
	}
	private WebDriver getChromeDriver() {
		//WebDriver driver = null;
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); 
		options.addArguments("enable-automation"); 
		options.addArguments("--no-sandbox"); 
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation"); 
		options.addArguments("--disable-gpu");
		//options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}
	private WebDriver getHTMLUnitDriver() {
		HtmlUnitDriver unitDriver = new HtmlUnitDriver(true);
		//unitDriver.setJavascriptEnabled(true);
		return unitDriver;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
}
