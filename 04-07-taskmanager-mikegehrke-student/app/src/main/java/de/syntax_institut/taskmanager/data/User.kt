package de.syntax_institut.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // <--- autoGenerate ist wichtig!
    val name: String,
    val isFavorite: Boolean = false
)