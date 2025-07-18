package com.wqz.accuratetimingnext.repository

import com.wqz.accuratetimingnext.database.dao.GameRecordDao
import com.wqz.accuratetimingnext.database.entity.GameRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 竞速记录仓库
 * Created by Wu Qizhen on 2025.7.15
 */
class GameRecordRepository @Inject constructor(private val gameRecordDao: GameRecordDao) {
    /*suspend fun insert(
        playerId: Int,
        playerName: String,
        timeId: Int,
        timeName: String,
        expectedTimes: List<Long>,
        actualTimes: List<Long>,
        errorTimes: List<Long>,
        totalError: Long,
        competitionTimestamp: Long,
        competitionRound: Int,
        competitionTag: String = "竞速记录模式"
    ): Long {
        val gameRecord = GameRecord(
            playerId = playerId,
            playerName = playerName,
            timeId = timeId,
            timeName = timeName,
            expectedTimes = expectedTimes,
            actualTimes = actualTimes,
            errorTimes = errorTimes,
            totalError = totalError,
            competitionTimestamp = competitionTimestamp,
            competitionRound = competitionRound,
            competitionTag = competitionTag
        )
        return gameRecordDao.insert(gameRecord)
    }*/

    val allGameRecords: Flow<List<GameRecord>> = gameRecordDao.getAll()

    suspend fun insert(gameRecord: GameRecord): Long = gameRecordDao.insert(gameRecord)

    fun getByDate(date: String): Flow<List<GameRecord>> = gameRecordDao.getByDate(date)

    suspend fun getById(id: Int): GameRecord? = gameRecordDao.getById(id)

    suspend fun delete(id: Int) = gameRecordDao.deleteById(id)

    fun getByDateRange(start: Long, end: Long): Flow<List<GameRecord>> {
        return gameRecordDao.getByDateRange(start, end)
    }
}