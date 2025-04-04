package de.syntax_institut.taskmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.taskmanager.data.User
import de.syntax_institut.taskmanager.data.database.NoteDatabase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = NoteDatabase.getDatabase(application).userDao()

    val allUsers: StateFlow<List<User>> = userDao.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser.asStateFlow()

    val selectedUserName: StateFlow<String> = _selectedUser
        .map { it?.name ?: "Kein Benutzer ausgewählt" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Kein Benutzer ausgewählt")

    init {
        // ✅ Bob automatisch anlegen, wenn noch nicht vorhanden
        viewModelScope.launch {
            userDao.getAllUsers().firstOrNull()?.let { userList ->
                if (userList.none { it.name.trim().equals("Bob", ignoreCase = true) }) {
                    userDao.insertUser(User(name = "Bob"))
                }
            }
        }
    }

    fun insertUser(name: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            val user = User(name = name, isFavorite = isFavorite)
            userDao.insertUser(user)
        }
    }

    fun addUser(name: String, isFavorite: Boolean = false) {
        insertUser(name, isFavorite)
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUser(user)
        }
    }

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

    fun clearSelectedUser() {
        _selectedUser.value = null
    }
}