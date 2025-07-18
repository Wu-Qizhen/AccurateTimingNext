package com.wqz.accuratetimingnext.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wqz.accuratetimingnext.database.converter.ArrayConverters

/**
 * 玩家信息
 * Created by Wu Qizhen on 2025.7.3
 */
@Entity(tableName = "player")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerName: String,
    val numberOfCompetitions: Int = 0,
    val accumulationNumber: Int = 0,
    val accumulationError: Long = 0,
    val accumulationTime: Long = 0,
    // 替换为数组存储 4-10 次最佳记录（索引 0-6 对应 4-10 次）
    @TypeConverters(ArrayConverters::class)
    val bestRecords: IntArray = intArrayOf(-1, -1, -1, -1, -1, -1, -1),
    // 替换为平均最大/最小误差数组（索引 0-6 对应 4-10 次）
    @TypeConverters(ArrayConverters::class)
    val maximumAverageErrors: IntArray = intArrayOf(-1, -1, -1, -1, -1, -1, -1),
    @TypeConverters(ArrayConverters::class)
    val minimumAverageErrors: IntArray = intArrayOf(-1, -1, -1, -1, -1, -1, -1)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (playerName != other.playerName) return false
        if (numberOfCompetitions != other.numberOfCompetitions) return false
        if (accumulationNumber != other.accumulationNumber) return false
        if (accumulationError != other.accumulationError) return false
        if (accumulationTime != other.accumulationTime) return false
        if (!bestRecords.contentEquals(other.bestRecords)) return false
        if (!maximumAverageErrors.contentEquals(other.maximumAverageErrors)) return false
        if (!minimumAverageErrors.contentEquals(other.minimumAverageErrors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + playerName.hashCode()
        result = 31 * result + numberOfCompetitions
        result = 31 * result + accumulationNumber
        result = 31 * result + accumulationError.hashCode()
        result = 31 * result + accumulationTime.hashCode()
        result = 31 * result + bestRecords.contentHashCode()
        result = 31 * result + maximumAverageErrors.contentHashCode()
        result = 31 * result + minimumAverageErrors.contentHashCode()
        return result
    }
}