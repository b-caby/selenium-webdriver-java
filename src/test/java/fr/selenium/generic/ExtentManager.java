package fr.selenium.generic;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/// <summary>
/// Gestion de l'outil de reporting
/// </summary>
public class ExtentManager {

	private static String date = Long.toString(System.currentTimeMillis());
	private static ExtentReports extent;

	/// <summary>
	/// Recuperation du rapport en cours
	/// </summary>
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance(date + "_" + DriverManager.GetDriverName() + Config.filename);

		return extent;
	}

	/// <summary>
	/// Creation du rapport
	/// </summary>
	public static ExtentReports createInstance(String fileName) {
		// Creation dossier de sauvegarde
		String currentDir = System.getProperty("user.dir");
		String reportFile = currentDir + File.separator + Config.reportfolder + File.separator + fileName + ".html";
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFile);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setEncoding("iso-8859-1");
		htmlReporter.config().setDocumentTitle(date + Config.filename);
		htmlReporter.config().setReportName(Config.reportname);

		extent = new ExtentReports();
		extent.setSystemInfo("Environment", Config.environment);
		extent.setSystemInfo("UserName", Config.username);
		extent.attachReporter(htmlReporter);

		return extent;
	}
}
