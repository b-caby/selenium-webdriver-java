package fr.selenium.tests;

import fr.selenium.generic.DriverManager;
import fr.selenium.generic.HookManager;
import org.junit.Test;

public class ExempleTest extends HookManager {

	@Test
	public void testExemple() {
		test = extent.createTest("Monitoring", "Contrôle de la page d'accueil");
		// Déroulement complet du test
		try {
			test.info("Chargement de la page d'accueil");
			DriverManager.goToPage("");
		} catch (Exception e) {
			verificationErrors.append(e.toString());
			test.fail("Le test ne s'est pas terminé correctement");
		}
	}
}
