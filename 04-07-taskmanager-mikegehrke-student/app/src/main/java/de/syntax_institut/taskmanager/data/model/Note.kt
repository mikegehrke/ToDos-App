package de.syntax_institut.taskmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long= 0,
    val userId: Long = 0,
    val title: String,
    val text: String,
    val isDone: Boolean = false
)