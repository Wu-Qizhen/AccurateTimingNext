package com.wqz.accuratetimingnext.database.di

import android.content.Context
import com.wqz.accuratetimingnext.database.AppDatabase
import com.wqz.accuratetimingnext.database.dao.GameRecordDao
import com.wqz.accuratetimingnext.database.dao.PlayerDao
import com.wqz.accuratetimingnext.database.dao.TimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 数据库模块
 * Created by Wu Qizhen on 2025.7.3
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePlayerDao(database: AppDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    fun provideTimeDao(database: AppDatabase): TimeDao {
        return database.timeDao()
    }

    @Provides
    fun provideGameRecordDao(database: AppDatabase): GameRecordDao {
        return database.gameRecordDao()
    }

    /*// Player 数据库
    @Provides
    @Singleton
    fun providePlayerDatabase(
        @ApplicationContext context: Context
    ): PlayerDatabase {
        return Room.databaseBuilder(
            context,
            PlayerDatabase::class.java,
            "Player.db"
        ).build()
    }

    @Provides
    fun providePlayerDao(database: PlayerDatabase): PlayerDao {
        return database.playerDao()
    }

    // Time 数据库
    @Provides
    @Singleton
    fun provideTimeDatabase(
        @ApplicationContext context: Context
    ): TimeDatabase {
        return Room.databaseBuilder(
            context,
            TimeDatabase::class.java,
            "Time.db"
        ).build()
    }

    @Provides
    fun provideTimeDao(database: TimeDatabase): TimeDao {
        return database.timeDao()
    }

    // GameRecord 数据库
    @Provides
    @Singleton
    fun provideGameRecordDatabase(
        @ApplicationContext context: Context
    ): GameRecordDatabase {
        return Room.databaseBuilder(
            context,
            GameRecordDatabase::class.java,
            "GameRecord.db"
        ).build()
    }

    @Provides
    fun provideGameRecordDao(database: GameRecordDatabase): GameRecordDao {
        return database.gameRecordDao()
    }*/
}