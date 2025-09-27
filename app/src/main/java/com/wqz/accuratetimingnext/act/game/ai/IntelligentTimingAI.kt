package com.wqz.accuratetimingnext.act.game.ai

import kotlin.math.abs
import kotlin.random.Random

/**
 * AI 计时
 * Created by Wu Qizhen on 2024.7.15
 */
class IntelligentTimingAI {
    private var learningRate = 0.1 // 学习率
    private var explorationRate = 0.3 // 探索率
    private var reactionTime = 150L // 基础反应时间（毫秒）
    private var timingBias = 0.0 // 时间偏差倾向
    private var performanceHistory = mutableListOf<Double>() // 性能历史记录
    private var roundCount = 0 // 轮次计数

    // 难度等级对应的参数
    private val difficultyLevels = mapOf(
        1 to mapOf("learningRate" to 0.05, "explorationRate" to 0.5, "reactionTime" to 200L),
        2 to mapOf("learningRate" to 0.08, "explorationRate" to 0.4, "reactionTime" to 180L),
        3 to mapOf("learningRate" to 0.1, "explorationRate" to 0.3, "reactionTime" to 150L),
        4 to mapOf("learningRate" to 0.12, "explorationRate" to 0.2, "reactionTime" to 120L),
        5 to mapOf("learningRate" to 0.15, "explorationRate" to 0.1, "reactionTime" to 100L)
    )

    private var currentDifficulty = 3 // 当前难度等级

    // 计算 AI 的点击时机
    fun calculateAITiming(targetTime: Long, currentElapsedTime: Long): Long {
        roundCount++

        // 预测的最佳点击时间（考虑反应延迟）
        val predictedOptimalTime = targetTime - reactionTime

        // 如果当前时间已经超过预测时间，立即点击
        if (currentElapsedTime >= predictedOptimalTime) {
            return currentElapsedTime
        }

        // 添加基于学习的时间偏差
        val biasAdjustedTime = predictedOptimalTime + (timingBias * targetTime).toLong()

        // 探索策略：有一定概率随机探索不同的点击时机
        return if (Random.nextDouble() < explorationRate) {
            // 探索模式：在目标时间前后随机点击
            val explorationRange = (targetTime * 0.1).toLong() // 探索范围为目标时间的 10%
            val randomOffset = Random.nextLong(-explorationRange, explorationRange)
            predictedOptimalTime + randomOffset
        } else {
            // 利用模式：使用学习到的最佳策略
            biasAdjustedTime
        }
    }

    // 从每轮结果中学习
    fun learnFromRound(targetTime: Long, playerTime: Long, aiTime: Long) {
        val playerError = abs(playerTime - targetTime).toDouble()
        val aiError = abs(aiTime - targetTime).toDouble()

        // 计算本轮表现（误差越小表现越好）
        val performance = if (aiError < playerError) {
            1.0 - (aiError / targetTime) // AI 赢了
        } else if (aiError > playerError) {
            -1.0 + (playerError / targetTime) // AI 输了
        } else {
            0.0 // 平局
        }

        performanceHistory.add(performance)

        // 保持历史记录长度
        if (performanceHistory.size > 10) {
            performanceHistory.removeAt(0)
        }

        // 调整参数基于学习
        adjustParameters(performance, playerError, aiError, targetTime)

        // 根据表现调整难度
        adjustDifficulty()
    }

    private fun adjustParameters(
        performance: Double,
        playerError: Double,
        aiError: Double,
        targetTime: Long
    ) {
        // 学习率衰减（随着轮次增加，学习率逐渐降低）
        val decayedLearningRate = learningRate * (1.0 - roundCount / 1000.0).coerceAtLeast(0.01)

        // 调整时间偏差（如果 AI 点击太早或太晚）
        val timingAdjustment = when {
            aiError > playerError -> decayedLearningRate * (playerError - aiError) / targetTime
            else -> decayedLearningRate * performance * 0.1
        }
        timingBias += timingAdjustment

        // 限制时间偏差在合理范围内
        timingBias = timingBias.coerceIn(-0.2, 0.2)

        // 调整反应时间（基于误差大小）
        val reactionAdjustment = when {
            aiError > 100 -> 5L // 误差大时增加反应时间
            aiError < 20 -> -2L // 误差小时减少反应时间
            else -> 0L
        }
        reactionTime = (reactionTime + reactionAdjustment).coerceIn(50L, 300L)

        // 调整探索率（表现好时减少探索，表现差时增加探索）
        val avgPerformance = performanceHistory.average()
        explorationRate = when {
            avgPerformance > 0.5 -> explorationRate * 0.9 // 表现好，减少探索
            avgPerformance < -0.5 -> explorationRate * 1.1 // 表现差，增加探索
            else -> explorationRate
        }.coerceIn(0.05, 0.8)
    }

    private fun adjustDifficulty() {
        if (roundCount % 5 == 0) { // 每 5 轮调整一次难度
            val avgPerformance = performanceHistory.takeLast(5).average()

            currentDifficulty = when {
                avgPerformance > 0.7 -> (currentDifficulty + 1).coerceAtMost(5) // 表现太好，增加难度
                avgPerformance < -0.7 -> (currentDifficulty - 1).coerceAtLeast(1) // 表现太差，降低难度
                else -> currentDifficulty
            }

            // 应用新难度参数
            difficultyLevels[currentDifficulty]?.let { params ->
                learningRate = params["learningRate"] as Double
                explorationRate = params["explorationRate"] as Double
                reactionTime = params["reactionTime"] as Long
            }
        }
    }

    // 获取 AI 的智能数据（用于显示）
    fun getAIData(): Map<String, Any> {
        return mapOf(
            "difficulty" to currentDifficulty,
            "learningRate" to learningRate,
            "explorationRate" to explorationRate,
            "reactionTime" to reactionTime,
            "timingBias" to timingBias,
            "roundsPlayed" to roundCount,
            "avgPerformance" to performanceHistory.average()
        )
    }

    // 获取 AI 难度等级
    fun getAIDifficulty(): Int = currentDifficulty

    // 重置 AI（开始新游戏时）
    fun reset() {
        performanceHistory.clear()
        roundCount = 0
        timingBias = 0.0
        reactionTime = 150L
        currentDifficulty = 3
    }
}

/*
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
}*/

/*
class IntelligentTimingAI {
    // AI 难度级别（1-10）
    private var difficultyLevel = 3
    private val learningRate = 0.1f

    // 历史数据记录
    val historicalData = mutableListOf<RoundData>()
    private var patternRecognition = PatternRecognition()
    private var playerProfile = PlayerProfile()

    // 神经网络简单模拟
    private val neuralNetwork = SimpleNeuralNetwork()

    data class RoundData(
        val targetTime: Long,
        val playerTime: Long,
        val aiTime: Long,
        val playerError: Long,
        val aiError: Long,
        val timestamp: Long = System.currentTimeMillis()
    )

    // 计算 AI 的点击时机
    fun calculateAITiming(targetTime: Long, currentTime: Long): Long {
        if (currentTime >= targetTime) return currentTime

        val baseTiming = calculateBaseTiming(targetTime)
        val adaptiveAdjustment = calculateAdaptiveAdjustment(targetTime)
        val psychologicalFactor = calculatePsychologicalFactor()

        var aiTiming = baseTiming + adaptiveAdjustment + psychologicalFactor

        // 添加随机性模拟人类不确定性（随难度提高而减少）
        val randomness = (Random.nextDouble() * 200 - 100).toLong() * (11 - difficultyLevel) / 10
        aiTiming += randomness

        // 确保不会提前点击
        return maxOf(aiTiming, currentTime + 50)
    }

    private fun calculateBaseTiming(targetTime: Long): Long {
        // 基于目标时间的基本计算
        return when {
            targetTime < 1000 -> targetTime - 150 // 短时间提前较少
            targetTime < 3000 -> targetTime - 200 // 中等时间
            else -> targetTime - 250 // 长时间可提前更多
        }
    }

    private fun calculateAdaptiveAdjustment(targetTime: Long): Long {
        if (historicalData.isEmpty()) return 0L

        // 分析玩家模式
        val recentRounds = historicalData.takeLast(5)
        val playerTrend = analyzePlayerTrend(recentRounds)
        val targetPattern = patternRecognition.analyzeTargetPattern(targetTime)

        // 根据玩家表现调整策略
        return when {
            playerTrend > 50 -> 20L // 玩家常延迟，AI 可稍晚
            playerTrend < -50 -> -20L // 玩家常提前，AI 可稍早
            else -> 0L
        } + targetPattern
    }

    private fun calculatePsychologicalFactor(): Long {
        // 心理战术：偶尔制造压力或放松
        return when {
            historicalData.size % 10 == 9 -> 30L // 偶尔延迟制造压力
            historicalData.size % 15 == 14 -> -40L // 偶尔提前 surprise
            else -> 0L
        }
    }

    // 从每轮学习中进化
    fun learnFromRound(targetTime: Long, playerTime: Long, aiTime: Long) {
        val playerError = abs(playerTime - targetTime)
        val aiError = abs(aiTime - targetTime)

        val roundData = RoundData(targetTime, playerTime, aiTime, playerError, aiError)
        historicalData.add(roundData)

        // 分析学习
        analyzePerformance(roundData)
        adjustDifficulty()
        patternRecognition.updatePatterns(roundData)
        playerProfile.updateProfile(roundData)

        // 神经网络学习
        neuralNetwork.learnFromData(roundData)
    }

    private fun analyzePerformance(roundData: RoundData) {
        if (historicalData.size < 2) return

        // 计算移动平均误差
        val recentErrors = historicalData.takeLast(10).map { it.aiError }
        val avgError = recentErrors.average()

        // 如果表现稳定改善，提高难度
        if (avgError < 100 && recentErrors.isNotEmpty() && recentErrors.last() < 80) {
            difficultyLevel = minOf(10, difficultyLevel + 1)
        }
    }

    private fun analyzePlayerTrend(recentRounds: List<RoundData>): Long {
        if (recentRounds.size < 2) return 0L

        var trend = 0L
        for (i in 1 until recentRounds.size) {
            val currentError = recentRounds[i].playerError
            val previousError = recentRounds[i - 1].playerError
            trend += (currentError - previousError)
        }
        return trend / (recentRounds.size - 1)
    }

    private fun adjustDifficulty() {
        val recentPlayerPerformance = historicalData.takeLast(8).map { it.playerError }.average()
        val recentAIPerformance = historicalData.takeLast(8).map { it.aiError }.average()

        // 动态调整难度
        when {
            recentPlayerPerformance < 50 && recentAIPerformance > 100 -> {
                difficultyLevel = minOf(10, difficultyLevel + 2) // 玩家很强，AI 需要提升
            }

            recentPlayerPerformance > 150 && recentAIPerformance < 80 -> {
                difficultyLevel = maxOf(1, difficultyLevel - 1) // 玩家较弱，AI 降低难度
            }
        }
    }

    fun getAIDifficulty(): Int = difficultyLevel

    fun getAILearningProgress(): String {
        return when {
            historicalData.size < 5 -> "新手学习"
            historicalData.size < 15 -> "进阶适应"
            historicalData.size < 30 -> "高手模式"
            else -> "大师级 AI"
        }
    }

    // 获取 AI 统计数据
    fun getAIStats(): AIStats {
        val totalRounds = historicalData.size
        val avgError = historicalData.map { it.aiError }.average()
        val winRate = if (totalRounds > 0) {
            historicalData.count { it.aiError < it.playerError } * 100.0 / totalRounds
        } else 0.0

        return AIStats(totalRounds, avgError, winRate, difficultyLevel)
    }

    data class AIStats(
        val totalRounds: Int,
        val averageError: Double,
        val winRate: Double,
        val currentDifficulty: Int
    )
}

// 模式识别组件
class PatternRecognition {
    private val targetPatterns = mutableMapOf<Long, PatternData>()
    private val playerPatterns = mutableListOf<PlayerPattern>()

    fun analyzeTargetPattern(targetTime: Long): Long {
        // 识别特定时间点的模式
        val similarTargets = targetPatterns.keys.filter {
            abs(it - targetTime) < 500
        }

        if (similarTargets.isNotEmpty()) {
            val avgAdjustment =
                similarTargets.map { targetPatterns[it]!!.suggestedAdjustment }.average()
            return avgAdjustment.toLong()
        }
        return 0L
    }

    fun updatePatterns(roundData: IntelligentTimingAI.RoundData) {
        // 更新目标时间模式
        val patternData = targetPatterns.getOrPut(roundData.targetTime) { PatternData() }
        patternData.addRound(roundData.aiError, roundData.playerError)

        // 更新玩家模式
        updatePlayerPatterns(roundData)
    }

    private fun updatePlayerPatterns(roundData: IntelligentTimingAI.RoundData) {
        val playerTiming = roundData.playerTime - roundData.targetTime
        playerPatterns.add(PlayerPattern(roundData.targetTime, playerTiming))

        // 保持最近 50 个模式
        if (playerPatterns.size > 50) {
            playerPatterns.removeAt(0)
        }
    }

    class PatternData {
        private val errors = mutableListOf<Long>()
        var suggestedAdjustment = 0L

        fun addRound(aiError: Long, playerError: Long) {
            errors.add(aiError)
            // 基于历史误差调整建议
            if (errors.size >= 3) {
                suggestedAdjustment = (errors.takeLast(3).average() * 0.3).toLong()
            }
        }
    }

    data class PlayerPattern(val targetTime: Long, val timingOffset: Long)
}

// 玩家画像分析
class PlayerProfile {
    private var consistencyScore = 0.0
    private var riskTendency = 0.5 // 0 = 保守, 1 = 冒险
    private var learningAbility = 0.5

    fun updateProfile(roundData: IntelligentTimingAI.RoundData) {
        // 分析玩家一致性和学习能力
        updateConsistency(roundData.playerError)
        updateRiskTendency(roundData.playerTime - roundData.targetTime)
        updateLearningAbility()
    }

    private fun updateConsistency(error: Long) {
        // 简化的一致性计算
        consistencyScore = 0.7 * consistencyScore + 0.3 * (100 - minOf(error, 200L)) / 100.0
    }

    private fun updateRiskTendency(timingOffset: Long) {
        // 正数表示延迟（保守），负数表示提前（冒险）
        val tendency = if (timingOffset > 0) 0.3 else 0.7
        riskTendency = 0.8 * riskTendency + 0.2 * tendency
    }

    private fun updateLearningAbility() {
        // 基于历史表现评估学习能力（简化）
        learningAbility = minOf(1.0, learningAbility + 0.05)
    }
}

// 简化的神经网络模拟
class SimpleNeuralNetwork {
    private var weight1 = 0.5
    private var weight2 = 0.5
    private var bias = 0.1

    fun learnFromData(roundData: IntelligentTimingAI.RoundData) {
        // 简化学习过程：调整权重
        val targetInput = roundData.targetTime / 1000.0
        val errorOutput = (roundData.aiError - roundData.playerError) / 100.0

        // 简单梯度下降模拟
        val adjustment = errorOutput * 0.01
        weight1 += adjustment
        weight2 -= adjustment * 0.5
        bias += adjustment * 0.1

        // 限制权重范围
        weight1 = weight1.coerceIn(0.1, 0.9)
        weight2 = weight2.coerceIn(0.1, 0.9)
    }

    fun predict(targetTime: Long): Double {
        return (targetTime / 1000.0) * weight1 + weight2 + bias
    }
}*/