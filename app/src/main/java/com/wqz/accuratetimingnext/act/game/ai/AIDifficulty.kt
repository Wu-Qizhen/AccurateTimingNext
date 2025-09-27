package com.wqz.accuratetimingnext.act.game.ai

/**
 * AI 相关数据类
 * Created by Wu Qizhen on 2024.7.15
 */
data class AIDifficulty(
    val level: Int, // 1-10 难度等级
    val baseError: Long, // 基础误差
    val learningSpeed: Float, // 学习速度
    val adaptability: Float // 适应能力
)