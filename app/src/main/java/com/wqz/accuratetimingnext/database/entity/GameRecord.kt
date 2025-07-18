package com.wqz.accuratetimingnext.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 竞速记录
 * Created by Wu Qizhen on 2025.7.3
 */
@Entity(tableName = "game_record") // 确保表名匹配
data class GameRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "player_id")
    val playerId: Int,

    @ColumnInfo(name = "player_name")
    val playerName: String,

    @ColumnInfo(name = "time_id")
    val timeId: Int,

    @ColumnInfo(name = "time_name")
    val timeName: String,

    @ColumnInfo(name = "expected_times")
    val expectedTimes: List<Long>,

    @ColumnInfo(name = "actual_times")
    val actualTimes: List<Long>,

    @ColumnInfo(name = "error_times")
    val errorTimes: List<Long>,

    @ColumnInfo(name = "total_error")
    val totalError: Long,

    // 统一使用 game_timestamp 作为字段名
    @ColumnInfo(name = "game_timestamp")
    val gameTimestamp: Long, // Long 类型存储时间戳

    @ColumnInfo(name = "game_round")
    val gameRound: Int,

    @ColumnInfo(name = "game_tag")
    val gameTag: String = "竞速记录模式"
)