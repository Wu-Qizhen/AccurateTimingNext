package com.wqz.accuratetimingnext.database.dao

import androidx.room.*
import com.wqz.accuratetimingnext.database.entity.Time
import kotlinx.coroutines.flow.Flow

/**
 * 时间数据访问对象
 * Created by Wu Qizhen on 2025.7.3
 */
@Dao
interface TimeDao {
    @Insert
    suspend fun insert(time: Time): Long

    @Update
    suspend fun update(time: Time)

    @Query("UPDATE time SET time_name = :newTimeName WHERE id = :id")
    suspend fun updateTimeName(id: Int, newTimeName: String)

    @Delete
    suspend fun delete(time: Time)

    @Query("DELETE FROM time WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM time")
    fun getAll(): Flow<List<Time>>

    @Query("SELECT * FROM time WHERE id LIKE :searchTerm OR time_name LIKE :searchTerm")
    fun search(searchTerm: String): Flow<List<Time>>

    @Query("SELECT * FROM time WHERE id = :id")
    suspend fun getById(id: Int): Time?

    @Query("SELECT time_name FROM time WHERE id = :id")
    suspend fun getTimeNameById(id: Int): String?
}