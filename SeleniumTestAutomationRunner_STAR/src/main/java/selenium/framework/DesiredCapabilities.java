package selenium.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DesiredCapabilities {
	private WebDriver driver;
	private String browser;
	private String runMode;
	
	public WebDriver getDriver() {
		return this.driver;
	}
	public void setDriver() {
		WebDriver driver = null;
		if (getBrowser().toLowerCase().equals("chrome")) {
			driver = getChromeDriver();
		} else if (getBrowser().toLowerCase().equals("firefox")) {
			//driver = (FirefoxDriver) getFirefoxDriver();
			driver = getFirefoxDriver();
		} else if (getBrowser().toLowerCase().equals("ie")) {
			//driver = (InternetExplorerDriver) getInternetExplorerDriver();
			driver = getInternetExplorerDriver();
		} else if (getBrowser().toLowerCase().equals("htmlunit")) {
			driver = getHTMLUnitDriver();
		}
		this.driver = driver;
	}
	private WebDriver getChromeDriver() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); 
		options.addArguments("enable-automation"); 
		options.addArguments("disable-infobars");
		options.addArguments("disable-dev-shm-usage");
		options.addArguments("disable-browser-side-navigation"); 
		options.addArguments("disable-gpu");
		if (getRunMode().toLowerCase().equals("headless")) {
			options.setHeadless(true);
			options.addArguments("headless");
			options.addArguments("start-maximized");
			options.addArguments("disable-gpu");
			options.addArguments("disable-extensions");
			options.addArguments("no-sandbox");
			//options.addArguments("window-size=1980,960");
			//options.setBinary("/usr/bin/chromium-browser");
		}
		options.addArguments("screenshot");
		ChromeDriver driver = new ChromeDriver(options);
		return driver;
	}
	private WebDriver getFirefoxDriver() {
		FirefoxDriver driver = new FirefoxDriver();
		return driver;
	}
	private WebDriver getInternetExplorerDriver() {
		InternetExplorerDriver driver = new InternetExplorerDriver();
		return driver;
	}
	private WebDriver getHTMLUnitDriver() {
		HtmlUnitDriver unitDriver = new HtmlUnitDriver(true);
		//unitDriver.setJavascriptEnabled(true);
		return unitDriver;
	}
	public String getBrowser() {
		return this.browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getRunMode() {
		return this.runMode;
	}
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}
}
