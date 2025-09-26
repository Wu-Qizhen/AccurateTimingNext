package com.wqz.accuratetimingnext.act.game.ai

/**
 * AI 记忆单元
 * Created by Wu Qizhen on 2025.7.15
 */
data class AIMemory(
    val targetTime: Long,
    val humanReaction: Long, // 玩家反应时间
    val aiReaction: Long, // AI 反应时间
    val error: Long,
    val timestamp: Long = System.currentTimeMillis()
)