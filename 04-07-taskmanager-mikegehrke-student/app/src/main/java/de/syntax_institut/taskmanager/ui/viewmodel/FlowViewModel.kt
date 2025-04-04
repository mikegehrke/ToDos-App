package de.syntax_institut.taskmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class FlowViewModel(application: Application) : AndroidViewModel(application) {

    data class User(val id: Int, val name: String, val isFavorite: Boolean)

    private val _allUsers = MutableStateFlow(
        listOf(
            User(1, "Hannah", isFavorite = true),
            User(2, "Peter", isFavorite = false),
            User(3, "Martin", isFavorite = false)
        )
    )
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    val favoriteUsers: StateFlow<List<User>> = _allUsers
        .map { it.filter { user -> user.isFavorite } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addRandomUser() {
        val names = listOf("Anna", "Jonas", "Sophie", "Daniel", "Mia")
        val randomName = names.random() + Random.nextInt(100)
        val newId = (_allUsers.value.maxOfOrNull { it.id } ?: 0) + 1
        val newUser = User(newId, randomName, isFavorite = false)
        _allUsers.value += newUser
    }

    fun toggleFavorite(user: User) {
        _allUsers.value = _allUsers.value.map {
            if (it.id == user.id) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    // Zahlen-Logik
    private val intFlow = MutableStateFlow(0)

    val stringStateFlow: StateFlow<String> = intFlow
        .map { number -> "Die Zahl ist $number" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "Die Zahl ist 0")

    fun increase() {
        intFlow.value += 1
    }

    fun decrease() {
        viewModelScope.launch {
            intFlow.emit(intFlow.value - 1)
        }
    }
}