package fr.selenium.tests.GitHubBrowsing;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

import fr.selenium.generic.DriverManager;
import fr.selenium.generic.HookManager;

public class GitHubSearchTest extends HookManager {

	@Test
	public void testGitHubSearch() {
		test = extent.createTest("Recherche GitHub", "Vérification du fonctionnement de la recherche");
		// Déroulement complet du test
		try {
			DriverManager.goToPage("");
			DriverManager.sendKeys(driver.findElement(By.xpath("//input[@name='q']")), "selenium");
			driver.findElement(By.xpath("//input[@name='q']")).sendKeys(Keys.ENTER);
			// Vérification présence Sélénium HQ
			try {
				assertTrue(driver.findElement(By.xpath("//h3[1]")).getText().matches("^[\\s\\S]*repository results[\\s\\S]*$"));
				test.pass("OK");
			} catch (Error e) {
				verificationErrors.append(e.toString());
				test.fail("KO");
			}
			try {
				assertTrue(DriverManager.isElementPresent(By.xpath("//a[@href='/SeleniumHQ/selenium']")));
				test.pass("OK");
			} catch (Error e) {
				verificationErrors.append(e.toString());
				test.fail("KO");
			}

		} catch (Exception e) {
			verificationErrors.append(e.toString());
			test.fail("Le test ne s'est pas terminé correctement");
		}
	}
}
