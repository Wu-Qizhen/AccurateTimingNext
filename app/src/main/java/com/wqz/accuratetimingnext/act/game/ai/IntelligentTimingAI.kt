package com.wqz.accuratetimingnext.act.game.ai

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * AI 计时
 * Created by Wu Qizhen on 2025.7.15
 */
class IntelligentTimingAI {
    private val memoryBank = mutableListOf<AIMemory>()
    private var currentDifficulty = AIDifficulty(
        level = 3,
        baseError = 150L,
        learningSpeed = 0.1f,
        adaptability = 0.5f
    )

    private var performance = AIPerformance()
    private var neuralWeights = FloatArray(10) { Random.nextFloat() } // 简化神经网络权重

    // 基于玩家表现调整 AI 难度
    private fun adjustDifficulty(playerAverageError: Long, playerConsistency: Float) {
        val improvementNeeded = playerAverageError < performance.averageError
        val isPlayerConsistent = playerConsistency > 0.7f

        currentDifficulty = when {
            improvementNeeded && isPlayerConsistent -> {
                // 玩家表现好，增加难度
                currentDifficulty.copy(
                    level = min(10, currentDifficulty.level + 1),
                    baseError = max(20L, currentDifficulty.baseError - 10L),
                    learningSpeed = currentDifficulty.learningSpeed * 1.1f
                )
            }

            !improvementNeeded && playerAverageError > 500L -> {
                // 玩家表现差，降低难度
                currentDifficulty.copy(
                    level = max(1, currentDifficulty.level - 1),
                    baseError = currentDifficulty.baseError + 20L,
                    adaptability = currentDifficulty.adaptability * 0.9f
                )
            }

            else -> currentDifficulty // 保持当前难度
        }
    }

    // 预测玩家行为模式
    private fun predictPlayerPattern(targetTime: Long): Any {
        val similarMemories = memoryBank.filter {
            abs(it.targetTime - targetTime) < 500L
        }.takeLast(5) // 最近 5 次类似情况

        if (similarMemories.isEmpty()) return 0.5f

        val avgHumanDelay = similarMemories.map { it.humanReaction }.average()
        val normalizedDelay = (avgHumanDelay / targetTime.toFloat()).coerceIn(0.1, 0.9)

        // 使用神经网络权重调整预测
        val prediction = normalizedDelay * neuralWeights[0] +
                performance.consistency * neuralWeights[1]

        return prediction.coerceIn(0.1, 0.9)
    }

    // 计算 AI 的反应时间（核心算法）
    fun calculateAITiming(targetTime: Long, playerCurrentTime: Long): Long {
        // 1. 预测玩家可能的表现
        val playerPrediction = predictPlayerPattern(targetTime)

        // 2. 基于难度生成基础误差
        val baseError = (currentDifficulty.baseError * (0.8f + Random.nextFloat() * 0.4f)).toLong()

        // 3. 学习调整：如果玩家有模式，AI 会学习并优化
        val learningAdjustment = if (memoryBank.size > 10) {
            val recentErrors = memoryBank.takeLast(10).map { it.error }
            val avgRecentError = recentErrors.average().toLong()
            (performance.averageError - avgRecentError) * currentDifficulty.learningSpeed
        } else {
            0L
        }

        // 4. 生成最终 AI 点击时间
        val aiTiming = targetTime + baseError + learningAdjustment.toLong()

        // 确保 AI 不会在玩家之前点击（公平竞争）
        return max(aiTiming, playerCurrentTime + 50L)
    }

    // 记录一轮结果用于学习
    fun learnFromRound(targetTime: Long, humanTime: Long, aiTime: Long) {
        val humanError = abs(humanTime - targetTime)
        val aiError = abs(aiTime - targetTime)

        val memory = AIMemory(
            targetTime = targetTime,
            humanReaction = humanTime,
            aiReaction = aiTime,
            error = aiError
        )

        memoryBank.add(memory)
        if (memoryBank.size > 100) { // 限制记忆大小
            memoryBank.removeAt(0)
        }

        // 更新性能指标
        updatePerformanceMetrics(humanError, aiError)

        // 调整神经网络权重（简化版反向传播）
        adjustNeuralWeights(humanError, aiError)
    }

    private fun updatePerformanceMetrics(humanError: Long, aiError: Long) {
        val totalGames = performance.gamesPlayed + 1
        val newAverageError =
            (performance.averageError * performance.gamesPlayed + aiError) / totalGames

        // 计算稳定性（误差方差越小越稳定）
        val recentErrors = memoryBank.takeLast(min(20, memoryBank.size)).map { it.error }
        val variance = if (recentErrors.size > 1) {
            val mean = recentErrors.average()
            recentErrors.map { (it - mean) * (it - mean) }.average()
        } else {
            0.0
        }
        val consistency = 1f - (variance / 10000f).toFloat().coerceIn(0f, 1f)

        performance = performance.copy(
            averageError = newAverageError,
            consistency = consistency,
            gamesPlayed = totalGames,
            learningRate = currentDifficulty.learningSpeed
        )
    }

    private fun adjustNeuralWeights(humanError: Long, aiError: Long) {
        // 简化版权重调整：如果 AI 表现比玩家差，调整权重学习
        val performanceGap = humanError - aiError

        neuralWeights = neuralWeights.mapIndexed { index, weight ->
            val adjustment = when {
                performanceGap > 50 -> weight * 1.01f // AI 表现好，轻微强化
                performanceGap < -50 -> weight * 0.99f // AI 表现差，减弱
                else -> weight // 保持
            }
            adjustment.coerceIn(0.1f, 2.0f)
        }.toFloatArray()
    }

    // 获取 AI 当前状态用于 UI 显示
    fun getAIStatus(): String {
        return when {
            performance.gamesPlayed < 5 -> "AI：学习中…"
            performance.averageError < 100 -> "AI：专家级"
            performance.averageError < 200 -> "AI：高手级"
            else -> "AI：普通级"
        }
    }

    fun getAIDifficulty(): Int = currentDifficulty.level
}