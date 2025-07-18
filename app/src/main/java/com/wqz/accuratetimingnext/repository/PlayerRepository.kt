package com.wqz.accuratetimingnext.repository

import com.wqz.accuratetimingnext.database.dao.PlayerDao
import com.wqz.accuratetimingnext.database.entity.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 玩家仓库类，用于访问数据库
 * Created by Wu Qizhen on 2025.7.3
 */
class PlayerRepository @Inject constructor(
    private val playerDao: PlayerDao
) {
    // 直接暴露 Flow，不进行错误处理
    val allPlayers: Flow<List<Player>> = playerDao.getAll()

    fun search(searchTerm: String): Flow<List<Player>> = playerDao.search(searchTerm)

    suspend fun getById(id: Int): Player? = playerDao.getById(id)

    suspend fun insert(player: Player): Long = playerDao.insert(player)

    suspend fun update(player: Player) = playerDao.update(player)

    suspend fun delete(player: Player) = playerDao.delete(player)

    suspend fun deleteById(id: Int) = playerDao.deleteById(id)
}