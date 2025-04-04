package de.syntax_institut.taskmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.ui.AppStart
import de.syntax_institut.taskmanager.data.model.TaskManagerTheme
import de.syntax_institut.taskmanager.ui.viewmodel.SettingsViewModel

// ✅ DataStore Definition (global)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkMode by settingsViewModel.darkMode.collectAsState()

            // ✅ Standard-Theme verwenden (TaskManagerTheme kümmert sich selbst um dark/light)
            TaskManagerTheme(useDarkTheme = isDarkMode) {
                AppStart()
            }
        }
    }
}