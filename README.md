
⸻

TaskManager – Your smart multi-user ToDo & Notes App

Built with Kotlin + Jetpack Compose + Room

About the project

TaskManager is a lightweight yet powerful productivity app that supports multiple users, allowing everyone to manage their personal notes and tasks.
Each note is assigned to a user, and all data is stored locally using Room with a one-to-many relationship.

Built with Clean Architecture, modern Jetpack Compose UI, and StateFlow for a reactive experience.

Developer GitHub Profile:
github.com/mikegehrke

⸻

Features
	•	✅ Multi-user support – Create, select, and delete users
	•	✅ Note management – Add, edit, delete, and mark notes as done
	•	✅ One-to-many database – Each note is linked to a user
	•	✅ Reactive Flow – Instant updates via StateFlow
	•	✅ Modern UI – Compose layout with BottomSheet, Dropdowns, FABs
	•	✅ Clean Navigation – With @Serializable routes and NavHost
	•	✅ AppStart counter & Username dialog – Productivity tracking built-in

⸻

Tech Stack

Layer	Technology
Language	Kotlin
UI	Jetpack Compose
Database	Room (One-to-many support)
Architecture	MVVM + Clean Architecture
State	StateFlow + Live Recomposition
Navigation	Kotlinx Serialization (@Serializable)
Optional	Hilt for DI



⸻

Project Structure

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
│   └── AppStart.kt
└── MainActivity.kt



⸻

Getting Started
	1.	Clone the repository

git clone [(https://github.com/mikegehrke/ToDos-App)]


	2.	Open in Android Studio
	3.	Build & run the app
Use an emulator or physical device.

⸻

Demo / Screenshots

(Insert screenshots or app preview GIF here. Let me know if you want help creating one!)

⸻

Why it’s great for your portfolio

This app demonstrates:
	•	Full Room DB integration with custom relationships
	•	Clean MVVM architecture and StateFlow usage
	•	Reusable Compose components with Material 3
	•	Typed and safe navigation via Kotlinx Serialization
	•	Real-world use cases: users, notes, filters, bottom sheets, and dialogs

Perfect to showcase your Android skills on your GitHub profile!

⸻

License

This project is licensed under the MIT License.
Feel free to fork, contribute, or use it as a base for your own project.

⸻

Created by mikegehrke
Powered by Kotlin, Compose, Flow & Room

⸻

