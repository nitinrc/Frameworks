package com.star.core;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import io.github.bonigarcia.wdm.WebDriverManager;

@Data
public class BrowserConfig {
    private WebDriver driver;
    private String browser;

    public BrowserConfig(String browser) {
        this.browser = browser;
        if (browser.equalsIgnoreCase(String.valueOf(Browser.CHROME)) || browser.equalsIgnoreCase(String.valueOf(Browser.CHROME_HEADLESS))) {
            setDriver(getChromeDriver());
        } else if (browser.equalsIgnoreCase(String.valueOf(Browser.FIREFOX))) {
            setDriver(getFirefoxDriver());
        } else if (browser.equalsIgnoreCase(String.valueOf(Browser.IE))) {
            setDriver(getIEDriver());
        } else if (browser.equalsIgnoreCase(String.valueOf(Browser.HTMLUNIT))) {
            setDriver(getHTMLUnitDriver());
        }
    }

    public WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (browser.equalsIgnoreCase(String.valueOf(Browser.CHROME))) {
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("disable-infobars");
            options.addArguments("disable-dev-shm-usage");
            options.addArguments("disable-browser-side-navigation");
            options.addArguments("disable-gpu");
        } else if (browser.equalsIgnoreCase(String.valueOf(Browser.CHROME_HEADLESS))) {
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-setuid-sandbox");
            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--disable-dev-shm-using");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("start-maximized");
            options.addArguments("disable-infobars");
            options.addArguments("--headless");
            //options.setHeadless(true);
            //options.addArguments("window-size=1980,960");
            //options.setBinary("/usr/bin/chromium-browser");
        }
        options.addArguments("screenshot");
        ChromeDriver driver = new ChromeDriver(options);
        return driver;
    }

    public WebDriver getFirefoxDriver() {
        FirefoxDriver driver = new FirefoxDriver();
        return driver;
    }

    public WebDriver getIEDriver() {
        InternetExplorerDriver driver = new InternetExplorerDriver();
        return driver;
    }

    public WebDriver getHTMLUnitDriver() {
        HtmlUnitDriver driver = new HtmlUnitDriver(true);
        //driver.setJavascriptEnabled(true);
        return driver;
    }
}

