package com.wqz.accuratetimingnext.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * List<Long> 转换器
 * Created by Wu Qizhen on 2025.7.3
 */
object ListConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromLongList(list: List<Long>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLongList(json: String?): List<Long>? {
        return json?.let {
            val type = object : TypeToken<List<Long>>() {}.type
            gson.fromJson(it, type)
        }
    }
}