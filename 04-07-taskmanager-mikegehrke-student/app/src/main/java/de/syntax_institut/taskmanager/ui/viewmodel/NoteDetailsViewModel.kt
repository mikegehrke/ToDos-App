package de.syntax_institut.taskmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import de.syntax_institut.taskmanager.data.database.NoteDatabase
import de.syntax_institut.taskmanager.data.model.Note
import de.syntax_institut.taskmanager.ui.NoteDetailsRoute
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    // Hole noteId aus der Route
    private val noteId = savedStateHandle.toRoute<NoteDetailsRoute>().noteId
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    // Lade Notiz mit der ID
    val noteStateFlow: StateFlow<Note> = noteDao.getNoteById(noteId)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Note(id = 0, title = "", text = "", isDone = false)
        )

    // ✅ Notiz aktualisieren
    fun updateNote(title: String, text: String, isDone: Boolean) {
        viewModelScope.launch {
            val updatedNote = noteStateFlow.value.copy(
                title = title,
                text = text,
                isDone = isDone
            )
            noteDao.update(updatedNote)
        }
    }

    // ✅ Notiz löschen
    fun deleteNote() {
        viewModelScope.launch {
            noteDao.delete(noteStateFlow.value)
        }
    }

    // ✅ Status erledigt/unerledigt toggeln
    fun toggleDoneStatus() {
        viewModelScope.launch {
            val current = noteStateFlow.value
            noteDao.update(current.copy(isDone = !current.isDone))
        }
    }
}