package de.syntax_institut.taskmanager.data.database

import androidx.room.*
import de.syntax_institut.taskmanager.data.User
import de.syntax_institut.taskmanager.data.model.Note
import de.syntax_institut.taskmanager.data.model.UserWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // --- NOTES ---

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllItems(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): Flow<Note?>

    @Query("SELECT * FROM notes WHERE isDone = 0 ORDER BY title ASC")
    fun getUndoneNotesSortedByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isDone = 0 ORDER BY id ASC")
    fun getUndoneNotesSortedByDate(): Flow<List<Note>>

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getNotesSortedByTitle(): Flow<List<Note>>

    // --- USER ---

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): Flow<User?>
    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithTasks(): Flow<List<UserWithTasks>>
}