package com.wqz.accuratetimingnext.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wqz.accuratetimingnext.database.entity.GameRecord
import kotlinx.coroutines.flow.Flow

/**
 * 记录数据访问对象
 * Created by Wu Qizhen on 2025.7.15
 */
@Dao
interface GameRecordDao {
    @Insert
    suspend fun insert(gameRecord: GameRecord): Long

    @Query("SELECT * FROM game_record")
    fun getAll(): Flow<List<GameRecord>>

    @Query(
        """
        SELECT * FROM game_record
        WHERE strftime('%Y-%m-%d', 
              datetime(game_timestamp / 1000, 'unixepoch')) = :date
    """
    )
    fun getByDate(date: String): Flow<List<GameRecord>>

    @Query("SELECT * FROM game_record WHERE id = :id")
    suspend fun getById(id: Int): GameRecord?

    @Query("DELETE FROM game_record WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM game_record WHERE game_timestamp BETWEEN :start AND :end")
    fun getByDateRange(start: Long, end: Long): Flow<List<GameRecord>>
}