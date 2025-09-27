package com.wqz.accuratetimingnext.act.game.ai

/**
 * AI 相关数据类
 * Created by Wu Qizhen on 2024.7.15
 */
data class AIPerformance(
    val averageError: Long = 0L,
    val consistency: Float = 0f, // 0-1 之间，越高越稳定
    val learningRate: Float = 0f,
    val gamesPlayed: Int = 0
)