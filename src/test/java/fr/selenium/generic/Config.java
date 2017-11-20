package fr.selenium.generic;

public class Config {
	// WebDriver management
	public static String chromeDriverLocation = "#LOCATION OF CHROME DRIVER#";
	public static String firefoxDriverLocation = "#LOCATION OF FIREFOX DRIVER#";
	public static String explorerDriverLocation = "#LOCATION OF IE DRIVER#";
	public static Integer timeoutValue = 20;
	// Extent management
	public static String filename = "_nonregressionTests";
	public static String reportname = "Non-regression tests";
	public static String reportfolder = "test-reports";
	public static String username = "b-caby";
	public static String environment = "Test Environment";
	// Hook Manager
	public static String screenname = "_screens";
	public static String browserFilePath = "../test-screens/";
	// ScreenShot Manager
	public static String screenfolder = "test-screens";

	public static String baseUrl = "https://github.com";
}
