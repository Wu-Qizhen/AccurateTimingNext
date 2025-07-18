package com.wqz.accuratetimingnext.repository

import com.wqz.accuratetimingnext.database.dao.TimeDao
import com.wqz.accuratetimingnext.database.entity.Time
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 仓库类，用于访问数据库
 * Created by Wu Qizhen on 2025.7.3
 */
class TimeRepository @Inject constructor(
    private val timeDao: TimeDao
) {
    // 直接暴露 Flow，不进行错误处理
    val allTimes: Flow<List<Time>> = timeDao.getAll()

    fun search(searchTerm: String): Flow<List<Time>> = timeDao.search(searchTerm)

    suspend fun getById(id: Int): Time? = timeDao.getById(id)

    suspend fun getTimeNameById(id: Int): String? = timeDao.getTimeNameById(id)

    suspend fun insert(time: Time): Long = timeDao.insert(time)

    suspend fun update(time: Time) = timeDao.update(time)

    suspend fun updateTimeName(id: Int, newTimeName: String) =
        timeDao.updateTimeName(id, newTimeName)

    suspend fun deleteById(id: Int) = timeDao.deleteById(id)

    suspend fun delete(time: Time) = timeDao.delete(time)
}