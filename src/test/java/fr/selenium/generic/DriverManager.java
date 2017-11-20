package fr.selenium.generic;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/// <summary>
/// Webdriver management
/// </summary>
public class DriverManager {

	protected static WebDriver driver;

	/// <summary>
	/// Get current driver
	/// </summary>
	public static WebDriver getDriver() {
		if (driver == null) {
			createDriver();
		}
		return driver;
	}
	
	/// <summary>
	/// Create the WebDriver with the correct web browser
	/// </summary>
	public static WebDriver createDriver() {
		String browser = "";
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
		Properties properties = new Properties();
		
		if (inputStream != null) {
			try {
				properties.load(inputStream);
				browser = properties.getProperty("app.browser");
			} catch (Exception e) {System.out.println(e.getMessage()); }
		}
		
		switch (browser) {
		default:
			case "chrome" :
				driver = createChromeDriver();
				break;
			case "firefox" :
				driver = createFirefoxDriver();
				break;
			case "ie":
				driver = createIEDriver();
				break;
		}
		return driver;
	}

	/// <summary>
	/// Chrome Webdriver creation
	/// </summary>
	public static WebDriver createChromeDriver() {
		System.setProperty("webdriver.chrome.driver", Config.chromeDriverLocation);
		// Management of bad SSL certificates
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		// Chromedriver creation
		driver = new ChromeDriver(capability);
		driver.manage().window().setPosition(new Point(0,0));
		driver.manage().window().setSize(new Dimension(1680,1050));
		driver.manage().timeouts().implicitlyWait(Config.timeoutValue, TimeUnit.SECONDS);
		return driver;
	}
	
	/// <summary>
	/// Firefox Webdriver creation
	/// </summary>
	public static WebDriver createFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", Config.firefoxDriverLocation);
		// Management of bad SSL certificates
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile myProfile = allProfiles.getProfile("CertificateIssue");
		myProfile.setAcceptUntrustedCertificates(true);
		myProfile.setAssumeUntrustedCertificateIssuer(false);
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(myProfile);
		// Firefoxdriver creation
		driver = new FirefoxDriver(options);
		driver.manage().window().setPosition(new Point(0,0));
		driver.manage().window().setSize(new Dimension(1680,1050));
		driver.manage().timeouts().implicitlyWait(Config.timeoutValue, TimeUnit.SECONDS);
		return driver;
	}
	
	/// <summary>
	/// Internet Explorer Webdriver creation
	/// </summary>
	public static WebDriver createIEDriver() {
		System.setProperty("webdriver.ie.driver", Config.explorerDriverLocation);
		// Management of bad SSL certificates
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		// IEdriver creation
		driver = new InternetExplorerDriver(capability);
		driver.manage().window().setPosition(new Point(0,0));
		driver.manage().window().setSize(new Dimension(1680,1050));
		driver.manage().timeouts().implicitlyWait(Config.timeoutValue, TimeUnit.SECONDS);
		return driver;
	}
	
	/// <summary>
	/// Override of Selenium functions
	/// </summary>
	public static void goToPage(String pageUrl) {
		driver.get(Config.baseUrl + pageUrl);
	}
	
	public static void sendKeys(final WebElement element, final String keys) {
		try {
			for (int i = 0; i < keys.length(); i++) {
				element.sendKeys(Character.toString(keys.charAt(i)));
				Thread.sleep(100);
				while (!element.getAttribute("value").equals(keys.substring(0, i+1))) {
					element.sendKeys(Character.toString(keys.charAt(i)));
					Thread.sleep(100);
				}
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Config.timeoutValue, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).pollingEvery(1, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.attributeContains(element, "value", keys.substring(0, i+1)));
			}
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public static boolean isElementVisible (By by) {
		try {
			if (driver.findElement(by).isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	/// <summary>
	/// Get driver name 
	/// </summary>
	public static String GetDriverName() {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		
		return browserName;
	}
	
	/// <summary>
	/// Get driver version 
	/// </summary>
	public static String GetDriverVersion() {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserVersion = caps.getVersion();
		
		return browserVersion;
	}
	
	
	public static boolean waitForElement(By by) throws Exception {
		ExpectedCondition<Boolean> condition = createWaitingConditionForNewElement(by);
		return createWaitObject(condition);
	}

	private static boolean createWaitObject(ExpectedCondition<Boolean> condition) throws Exception {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Config.timeoutValue, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).pollingEvery(1, TimeUnit.SECONDS);
		return wait.until(condition);
	}

	private static ExpectedCondition<Boolean> createWaitingConditionForNewElement(final By by) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Boolean loaded = false;
				try {
					WebElement element = driver.findElement(by);
					if (element.isDisplayed()) {
						loaded = true;
					}
				} catch (Exception e) {
					loaded = false;
				}
				return loaded;
			}
		};
	}
}
