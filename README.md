TaskManager

TaskManager ist eine einfache und effiziente App zur Verwaltung von Aufgaben und Benutzern. Die App bietet Funktionen zur Erstellung und Verwaltung von Notizen und Aufgaben für mehrere Benutzer. Benutzer können Notizen erstellen, bearbeiten, löschen und den Status (z. B. erledigt oder nicht erledigt) ändern. Die App nutzt Room für die lokale Datenbank und folgt der Architektur der Clean Architecture.

Funktionen
	•	Benutzerverwaltung: Erstellen, Löschen und Auswählen von Benutzern
	•	Notizenverwaltung: Erstellen, Bearbeiten, Löschen und Markieren von Notizen als erledigt
	•	One-to-Many-Beziehung: Jede Notiz ist einem Benutzer zugeordnet
	•	Filter und Sortierung: Notizen nach Status (erledigt/nicht erledigt) filtern und sortieren
	•	UI: Intuitive Benutzeroberfläche mit BottomNavigationBar und FloatingActionButton (FAB)

Technologien
	•	Kotlin – Hauptsprache der App
	•	Jetpack Compose – Moderne UI mit deklarativer Syntax
	•	Room – Lokale Datenbank für User & Notes
	•	StateFlow – Reaktive Zustandsverwaltung
	•	Hilt – Dependency Injection
	•	Navigation mit @Serializable-Routen – für sauberen und typensicheren Seitenwechsel

Voraussetzungen
	•	Android Studio (Kotlin 1.9+ empfohlen)
	•	Compose BOM 2024.04.01
	•	Internetverbindung für Abhängigkeitsauflösung

Installation
	1.	Klone das Repository:
git clone https://github.com/DEIN_USERNAME/TaskManager.git
	2.	Öffne das Projekt in Android Studio.
	3.	Sync die Gradle-Abhängigkeiten und starte das Projekt mit einem Emulator oder echtem Gerät.

Projektstruktur
	•	data: enthält Room-Datenbank, DAOs, Entities
	•	domain: enthält Models und Business-Logik
	•	ui: enthält alle Screens, Komponenten und Navigation
	•	viewmodels: getrennte ViewModels für Notizen und Benutzer.
Verzeichnisstruktur

TaskManager/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/
│   │   │   │   │   └── deinname/
│   │   │   │   │       ├── data/
│   │   │   │   │       ├── domain/
│   │   │   │   │       └── ui/
│   │   │   │   │           ├── screens/
│   │   │   │   │           └── components/
│   │   │   │   ├── res/
│   │   │   │   └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── README.md

Beiträge

Wenn du zur Weiterentwicklung des Projekts beitragen möchtest, fork das Repository, erstelle einen Branch, nehme deine Änderungen vor und reiche eine Pull-Anfrage ein.

Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert – siehe die LICENSE-Datei für Details.


