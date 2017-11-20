package fr.selenium.generic;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;

// Importation suite de tests
// Doit comprendre l'ensemble de tous les tests de la solution
import fr.selenium.tests.ExempleTest;
import fr.selenium.tests.GitHubBrowsing.GitHubRepoTest;
import fr.selenium.tests.GitHubBrowsing.GitHubSearchTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ ExempleTest.class, GitHubRepoTest.class, GitHubSearchTest.class})
public class LauncherManager {

	@BeforeClass
	public static void startTest() {
		System.out.println("Starting test suite");
	}

	@AfterClass
	public static void TearDown() {
		WebDriver driver = DriverManager.getDriver();
		driver.quit();
	}
}