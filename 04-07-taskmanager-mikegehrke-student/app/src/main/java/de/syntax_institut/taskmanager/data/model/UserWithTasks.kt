package de.syntax_institut.taskmanager.data.model

import androidx.room.Embedded
import androidx.room.Relation
import de.syntax_institut.taskmanager.data.User

data class UserWithTasks(
    @Embedded val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val notes: List<Note>
)