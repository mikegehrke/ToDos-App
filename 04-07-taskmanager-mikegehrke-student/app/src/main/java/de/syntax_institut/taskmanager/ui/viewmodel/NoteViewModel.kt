package de.syntax_institut.taskmanager.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.taskmanager.data.User
import de.syntax_institut.taskmanager.data.database.NoteDatabase
import de.syntax_institut.taskmanager.data.model.Note
import de.syntax_institut.taskmanager.data.model.UserWithTasks
import de.syntax_institut.taskmanager.dataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SortType { DATE, TITLE }

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val APP_START_COUNT_KEY = intPreferencesKey("app_start_count")
        private val SORT_NOTES_BY_KEY = stringPreferencesKey("sort_notes_by")
    }

    private val dataStore = application.dataStore
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    private val _sortType = MutableStateFlow(SortType.DATE)
    val sortType = _sortType.asStateFlow()

    private val _showOnlyUndone = MutableStateFlow(false)
    val showOnlyUndone = _showOnlyUndone.asStateFlow()

    val notes: StateFlow<List<Note>> = combine(
        noteDao.getAllItems(),
        _sortType,
        _showOnlyUndone
    ) { notes, sortType, onlyUndone ->
        notes
            .filter { if (onlyUndone) !it.isDone else true }
            .sortedWith(compareBy {
                when (sortType) {
                    SortType.DATE -> it.id
                    SortType.TITLE -> it.title.lowercase()
                }
            })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userName = dataStore.data
        .map { it[NAME_KEY] ?: "No Name" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "No Name")

    val appStartCount = dataStore.data
        .map { it[APP_START_COUNT_KEY] ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val sortSettingsFlow = dataStore.data
        .map { it[SORT_NOTES_BY_KEY] ?: "date" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "date")

    private fun observeSettings(settingsFlow: StateFlow<String>) {
        viewModelScope.launch {
            settingsFlow.collect { sortString ->
                val sort = when (sortString) {
                    "title" -> SortType.TITLE
                    "date" -> SortType.DATE
                    else -> SortType.DATE
                }
                _sortType.value = sort
            }
        }
    }

    // ✅ One-to-many: UserWithTasks
    val usersWithTasks: StateFlow<List<UserWithTasks>> = noteDao.getUsersWithTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Nur wenn kein User mit id = 0 vorhanden ist
        viewModelScope.launch {
            val existingUser = noteDao.getUserById(0).firstOrNull()
            if (existingUser == null) {
                val user = User(id = 0, name = "Max Mustermann", isFavorite = false)
                noteDao.insertUser(user)
            }
        }

        observeSettings(sortSettingsFlow)
    }

    private val _newNoteTitle = MutableStateFlow("")
    val newNoteTitle = _newNoteTitle.asStateFlow()

    private val _newNoteText = MutableStateFlow("")
    val newNoteText = _newNoteText.asStateFlow()

    fun onChangeNewNoteTitle(title: String) {
        _newNoteTitle.value = title
    }

    fun onChangeNewNoteText(text: String) {
        _newNoteText.value = text
    }

    fun insertNote(done: Boolean = false, userId: Long = 0L) {
        viewModelScope.launch {
            val note = Note(
                userId = userId, // ⬅️ korrekt zugewiesen
                title = _newNoteTitle.value,
                text = _newNoteText.value,
                isDone = done
            )
            noteDao.insert(note)
            _newNoteTitle.value = ""
            _newNoteText.value = ""
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun updateUserName(newName: String) {
        viewModelScope.launch {
            dataStore.edit { it[NAME_KEY] = newName }
        }
    }

    fun incrementAppStartCount() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                val current = prefs[APP_START_COUNT_KEY] ?: 0
                prefs[APP_START_COUNT_KEY] = current + 1
            }
            fun insertNote(author: User?, done: Boolean = false) {
                viewModelScope.launch {
                    val note = Note(
                        userId = author?.id ?: 0, // ⬅️ Fallback auf Bob wenn kein Autor ausgewählt
                        title = _newNoteTitle.value,
                        text = _newNoteText.value,
                        isDone = done
                    )
                    noteDao.insert(note)
                    _newNoteTitle.value = ""
                    _newNoteText.value = ""
                }
            }
        }
    }
}