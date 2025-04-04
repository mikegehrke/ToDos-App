TaskManager – Deine smarte ToDo- & Notiz-App

Entwickelt mit Kotlin + Jetpack Compose + Room

Über das Projekt

TaskManager ist eine produktive, leicht erweiterbare ToDo-App mit Mehrbenutzerverwaltung und sauberem Datenmodell.
Jede Notiz gehört zu einem Benutzer – alle Aufgaben sind somit personalisiert.
Zudem bietet die App Live-Filter, Status-Tracking, eine moderne UI und ein cleveres User-BottomSheet.

GitHub-Profil des Entwicklers:
github.com/mikegehrke

⸻

Features
	•	✅ Mehrere Benutzer verwalten – Nutzer erstellen, auswählen, löschen
	•	✅ Notizen verwalten – Erstellen, bearbeiten, löschen, als „erledigt“ markieren
	•	✅ One-to-Many – Jede Notiz gehört einem Benutzer (via userId)
	•	✅ Realtime-Flow – Alle Änderungen sind reaktiv via StateFlow
	•	✅ UI mit Compose – inklusive BottomSheet, Dropdowns, FAB-Menüs, Toggles
	•	✅ Navigation – mit @Serializable-Routen & NavHost
	•	✅ AppStart-Zähler & Username-Dialog – als Mini-Dashboard

⸻

Tech Stack

Layer	Technologie
Sprache	Kotlin
UI	Jetpack Compose
DB	Room (One-to-Many)
Architektur	MVVM + Flow
DI	Hilt (optional einsetzbar)
Navigation	@Serializable-Routen
State Mgmt	StateFlow / collectAsState



⸻

Projektstruktur

app/
├── data/
│   ├── NoteDao.kt
│   ├── UserDao.kt
│   ├── NoteDatabase.kt
│   └── entities/
├── viewmodel/
│   ├── NoteViewModel.kt
│   └── UserViewModel.kt
├── ui/
│   ├── screens/
│   │   ├── NoteScreen.kt
│   │   ├── FlowScreen.kt
│   │   ├── SettingsScreen.kt
│   │   └── NoteDetailsScreen.kt
│   └── components/
├── navigation/
│   └── AppStart.kt (NavHost)
└── MainActivity.kt



⸻

Installation & Ausführung
	1.	Clone das Repo

git clone https://github.com/mikegehrke/TaskManager.git


	2.	Öffne es in Android Studio
	3.	Starte die App
	•	Emulator oder echtes Gerät wählen
	•	App ausführen – fertig!

⸻

Screenshots / Vorschau

(Hier kannst du echte Screenshots einfügen – oder schick sie mir, ich bau sie ein!)

⸻

Nützlich für Bewerbungen

Diese App zeigt:
	•	Dein Verständnis von Room + Jetpack Compose
	•	Umgang mit Mehrbenutzer-Systemen
	•	Anwendung von StateFlow & MVVM
	•	UI-Konzepte mit Material 3 & Compose Components
	•	Navigation per Serializable-Routen (ideal für Prüfungen & Tests)
	•	Gute Clean Architecture

⸻

Mitwirken & Lizenz

Pull Requests willkommen!
Dieses Projekt ist unter der MIT License lizenziert.

⸻

Entwickelt von mikegehrke
Powered by Kotlin + Compose + Flow

⸻
