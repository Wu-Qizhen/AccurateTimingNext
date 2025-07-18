package com.wqz.accuratetimingnext.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wqz.accuratetimingnext.database.entity.Player
import kotlinx.coroutines.flow.Flow

/**
 * 玩家数据访问对象
 * Created by Wu Qizhen on 2025.7.3
 */
@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player): Long

    @Update
    suspend fun update(player: Player)

    @Delete
    suspend fun delete(player: Player)

    @Query("DELETE FROM player WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM player WHERE id = :id")
    suspend fun getById(id: Int): Player?

    @Query("SELECT * FROM player")
    fun getAll(): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE playerName LIKE :searchTerm OR id = :searchTerm")
    fun search(searchTerm: String): Flow<List<Player>>
}