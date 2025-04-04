package de.syntax_institut.taskmanager.data.database

import androidx.room.*
import de.syntax_institut.taskmanager.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): Flow<User?>
    @Dao
    interface UserDao {
        @Query("SELECT * FROM users ORDER BY id DESC")
        fun getAllUsers(): Flow<List<User>>
    }
}