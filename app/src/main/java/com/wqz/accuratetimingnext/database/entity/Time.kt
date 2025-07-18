package com.wqz.accuratetimingnext.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 时间
 * Created by Wu Qizhen on 2025.7.3
 */
@Entity(tableName = "time")
data class Time(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "time_name")
    val timeName: String,

    @ColumnInfo(name = "expected_times")
    val expectedTimes: List<Long>
)