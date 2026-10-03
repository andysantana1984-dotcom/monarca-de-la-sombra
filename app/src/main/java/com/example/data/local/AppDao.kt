package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    // Dungeons
    @Query("SELECT * FROM dungeons ORDER BY dungeonNumber ASC")
    fun getAllDungeons(): Flow<List<DungeonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDungeons(dungeons: List<DungeonEntity>)

    @Update
    suspend fun updateDungeon(dungeon: DungeonEntity)

    @Query("SELECT * FROM dungeons WHERE id = :id LIMIT 1")
    suspend fun getDungeonById(id: String): DungeonEntity?

    // Chat
    @Query("SELECT * FROM chat_messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun clearMessages()

    // Study Logs
    @Query("SELECT * FROM study_logs ORDER BY timestamp DESC")
    fun getAllStudyLogs(): Flow<List<StudyLogEntity>>

    @Insert
    suspend fun insertStudyLog(log: StudyLogEntity)
}
