package com.wqz.accuratetimingnext.database.converter

import androidx.room.TypeConverter

/**
 * 数组转换器
 * Created by Wu Qizhen on 2025.7.3
 */
object ArrayConverters {
    @TypeConverter
    fun fromIntArray(array: IntArray): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toIntArray(value: String): IntArray {
        return value.split(",").map { it.toInt() }.toIntArray()
    }
}