package fr.selenium.tests.GitHubBrowsing;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import fr.selenium.generic.DriverManager;
import fr.selenium.generic.HookManager;
import com.aventstack.extentreports.ExtentTest;

public class GitHubRepoTest extends HookManager {

	@Test
	public void testGitHubRepo() {
		test = extent.createTest("Recherche Repo", "Ouverture d'un repo et vérification de l'IHM");
		DriverManager.goToPage("/b-caby");

		ExtentTest childTest = test.createNode("Ouverture premier projet");
		// Déroulement complet du test - Node 1
		try {
			DriverManager.waitForElement(By.xpath("//a[@href='/b-caby/cucumber-selenium-webdriver']"));
			driver.findElement(By.xpath("//a[@href='/b-caby/cucumber-selenium-webdriver']")).click();

			DriverManager.waitForElement(By.xpath("//article/h1"));
			// Controle du titre de la page
			childTest.info("Le titre est " + driver.findElement(By.xpath("//article/h1")).getText());
			try {
				assertTrue(driver.findElement(By.xpath("//article/h1")).getText().matches("^[\\s\\S]*Automatisation tests fonctionnels via Cucumber-JS et Selenium Webdriver[\\s\\S]*$"));
				childTest.pass("OK");
			} catch (Error e) {
				verificationErrors.append(e.toString());
				childTest.fail("KO");
			}

		} catch (Exception e) {
			verificationErrors.append(e.toString());
			childTest.fail("Le test ne s'est pas terminé correctement");
		}

		ExtentTest childTest2 = test.createNode("Ouverture second projet");
		// Déroulement complet du test - Node 2
		try {
			DriverManager.goToPage("/b-caby");
			DriverManager.waitForElement(By.xpath("//a[@href='/b-caby/TacoOrder']"));
			driver.findElement(By.xpath("//a[@href='/b-caby/TacoOrder']")).click();

			DriverManager.waitForElement(By.xpath("//h1[@class='public ']"));
			// Controle du titre de la page
			childTest2.info("Le titre est " + driver.findElement(By.xpath("//h1[@class='public ']")).getText());
			try {
				assertTrue(driver.findElement(By.xpath("//h1[@class='public ']")).getText().matches("^[\\s\\S]*TacoOrder[\\s\\S]*$"));
				childTest2.pass("OK");
			} catch (Error e) {
				verificationErrors.append(e.toString());
				childTest2.fail("KO");
			}
		} catch (Exception e) {
			verificationErrors.append(e.toString());
			childTest2.fail("Le test ne s'est pas terminé correctement");
		}
	}

}
