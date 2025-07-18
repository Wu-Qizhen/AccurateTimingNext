package com.wqz.accuratetimingnext.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wqz.accuratetimingnext.database.converter.ArrayConverters
import com.wqz.accuratetimingnext.database.converter.ListConverters
import com.wqz.accuratetimingnext.database.dao.GameRecordDao
import com.wqz.accuratetimingnext.database.dao.PlayerDao
import com.wqz.accuratetimingnext.database.dao.TimeDao
import com.wqz.accuratetimingnext.database.entity.GameRecord
import com.wqz.accuratetimingnext.database.entity.Player
import com.wqz.accuratetimingnext.database.entity.Time

/**
 * 创建数据库
 * Created by Wu Qizhen on 2025.7.15
 */
@Database(
    entities = [Player::class, Time::class, GameRecord::class],
    version = 1, // 统一版本号
    exportSchema = false
)
@TypeConverters(ListConverters::class, ArrayConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun timeDao(): TimeDao
    abstract fun gameRecordDao(): GameRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App_Database" // 统一数据库名称
                )
                    .fallbackToDestructiveMigration() // 添加迁移策略
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}