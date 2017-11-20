package fr.selenium.generic;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import junit.framework.TestResult;
import static org.junit.Assert.fail;

public class HookManager {

	protected static ExtentReports extent;
	protected static WebDriver driver;
	protected static TestResult result;
	protected static StringBuffer verificationErrors;
	protected static ExtentTest test;

	@Before
	public void setUp() {
		driver = DriverManager.getDriver();
		extent = ExtentManager.getInstance();
		result = new TestResult();
		verificationErrors = new StringBuffer();
	}

	@After
	public void reportManagement() throws IOException {
		// Création du rapport de résultats
		String date = Long.toString(System.currentTimeMillis());
		String screenName = date + Config.screenname;
		ScreenShotManager.captureScreen(driver, screenName);
		String browserFilePath = Config.browserFilePath + screenName + ".png";
		test.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(browserFilePath).build());

		// Suppression cookies
		// Gestion du cas VAD
		driver.manage().deleteAllCookies();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			test.fail(verificationErrorString);
			extent.flush();
			fail(verificationErrorString);
		} else {
			test.pass("Success");
			extent.flush();
		}
	}

}
