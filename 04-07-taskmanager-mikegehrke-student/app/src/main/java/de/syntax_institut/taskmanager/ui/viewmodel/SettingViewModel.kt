package de.syntax_institut.taskmanager.ui.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.taskmanager.dataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = application.dataStore

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
        val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_on")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val DAILY_REMINDER_KEY = booleanPreferencesKey("daily_reminder")
        val FAVORITE_FIRST_KEY = booleanPreferencesKey("favorite_first")
        val SORT_NOTES_BY_KEY = stringPreferencesKey("sort_notes_by")
    }

    val userName: StateFlow<String> = dataStore.data
        .map { it[NAME_KEY] ?: "No Name" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "No Name")

    val isNotificationOnStateFlow: StateFlow<Boolean> = dataStore.data
        .map { it[NOTIFICATIONS_KEY] == true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val darkMode: StateFlow<Boolean> = dataStore.data
        .map { it[DARK_MODE_KEY] == true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val dailyReminder: StateFlow<Boolean> = dataStore.data
        .map { it[DAILY_REMINDER_KEY] == true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val favoriteFirst: StateFlow<Boolean> = dataStore.data
        .map { it[FAVORITE_FIRST_KEY] == true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val sortNotesBy: StateFlow<String> = dataStore.data
        .map { it[SORT_NOTES_BY_KEY] ?: "title" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "title")

    val isDarkModeState = darkMode // Alias für MainActivity

    // -- Actions:

    fun updateName(newName: String) {
        viewModelScope.launch {
            dataStore.edit { it[NAME_KEY] = newName }
        }
    }

    fun updateNotificationOn(value: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[NOTIFICATIONS_KEY] = value }
        }
    }

    fun setDarkMode(value: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[DARK_MODE_KEY] = value }
        }
    }

    fun toggleDarkMode(value: Boolean) = setDarkMode(value)

    fun toggleDailyReminder(value: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[DAILY_REMINDER_KEY] = value }
        }
    }

    fun toggleFavoriteFirst(value: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[FAVORITE_FIRST_KEY] = value }
        }
    }

    fun setSortNotesBy(value: String) {
        viewModelScope.launch {
            dataStore.edit { it[SORT_NOTES_BY_KEY] = value }
        }
    }
}