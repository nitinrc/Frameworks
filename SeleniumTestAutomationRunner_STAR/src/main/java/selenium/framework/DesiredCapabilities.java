package selenium.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DesiredCapabilities {
	public static WebDriver driver;
	public static String browser;
	
	public WebDriver getDriver(String browser) {
		WebDriver driver = null;
		if (browser.toLowerCase().equals("chrome")) {
			driver = getChromeDriver();
		} else if (browser.toLowerCase().equals("firefox")) {
			//driver = (FirefoxDriver) getFirefoxDriver();
		} else if (browser.toLowerCase().equals("ie")) {
			//driver = (InternetExplorerDriver) getInternetExplorerDriver();
		} else if (browser.toLowerCase().equals("htmlunit")) {
			driver = getHTMLUnitDriver();
		}
		DesiredCapabilities.driver = driver;
		return driver;
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
}
