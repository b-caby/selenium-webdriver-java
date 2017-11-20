package fr.selenium.generic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotManager {

	public static void captureScreen(WebDriver driver, String screenshotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		String screenshotFile = currentDir + File.separator + Config.screenfolder + File.separator + screenshotName + ".png";
		File destination = new File(screenshotFile);
		FileUtils.copyFile(source, destination);
	}
}
