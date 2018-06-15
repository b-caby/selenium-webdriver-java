# Automatisation tests fonctionnels via Selenium Webdriver & JAVA JUnit

## Pré-requis nécessaire au fonctionnement des tests

- Installer les driver correspondants aux navigateurs sur la machine de tests
- Spécifier dans le fichier de configuration l'emplacement physique des drivers
- Modifier le fichier de configuration avec les autres informations adaptées

## Execution des tests

Lancer la commande suivante sur la racine du projet :

mvn clean install -dbrower=chrome|firefox|ie (en fonction du choix de navigateur)
