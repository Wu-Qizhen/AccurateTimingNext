package com.wqz.accuratetimingnext.act.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.ai.IntelligentTimingAI
import com.wqz.accuratetimingnext.act.game.ui.StopWatchComponent
import com.wqz.accuratetimingnext.act.game.viewmodel.StopWatchViewModel
import com.wqz.accuratetimingnext.act.game.viewmodel.TimerState
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBorderColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XContentColor
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XCard
import com.wqz.accuratetimingnext.aethex.matrix.ui.XDivider
import com.wqz.accuratetimingnext.aethex.matrix.ui.XToast
import kotlin.math.abs

/**
 * 经典模式
 * Created by Wu Qizhen on 2025.7.15
 * Updated by Wu Qizhen on 2025.9.5
 */
class ClassicPracticeActivity : ComponentActivity() {
    private val aiPlayer = IntelligentTimingAI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(
                titleId = R.string.practice_mode
            ) {
                CompositionLocalProvider(LocalLifecycleOwner provides this) {
                    val viewModel: StopWatchViewModel = viewModel()
                    val expectedTimes =
                        intent.getLongArrayExtra("TIME")?.toList() ?: listOf(404L)

                    ClassicPracticeScreen(
                        viewModel = viewModel,
                        expectedTimes = expectedTimes,
                        aiPlayer = aiPlayer
                    )
                }
            }
        }
    }

    @Composable
    fun ClassicPracticeScreen(
        viewModel: StopWatchViewModel,
        expectedTimes: List<Long>,
        aiPlayer: IntelligentTimingAI
    ) {
        // 当前目标时间点索引
        var currentTargetIndex by remember { mutableIntStateOf(0) }
        /*// 总误差
        var totalError by remember { mutableLongStateOf(0L) }*/
        // 玩家总误差
        var playerTotalError by remember { mutableLongStateOf(0L) }
        // AI 总误差
        var aiTotalError by remember { mutableLongStateOf(0L) }
        // 游戏完成状态
        var gameFinished by remember { mutableStateOf(false) }
        // 玩家实际时间记录
        val playerActualTimes = remember { mutableListOf<Long>() }
        // AI 实际时间记录
        val aiActualTimes = remember { mutableListOf<Long>() }
        // 当前轮次结果
        var currentRoundResult by remember { mutableStateOf("") }

        // AI 的点击时间预测
        var aiPredictedTiming by remember { mutableLongStateOf(0L) }

        // 收集状态
        val timerState by viewModel.timerState.collectAsStateWithLifecycle()
        val currentTime by viewModel.currentTimeMillis.collectAsStateWithLifecycle()
        val displayText by viewModel.stopWatchText.collectAsStateWithLifecycle()

        /*// AI 对战逻辑
        LaunchedEffect(timerState, currentTargetIndex) {
            if (timerState == TimerState.RUNNING && !gameFinished) {
                val targetTime = expectedTimes[currentTargetIndex]

                // AI 计算自己的点击时间
                val aiTiming = aiPlayer.calculateAITiming(targetTime, currentTime)

                // 模拟 AI 点击（在实际时间到达时记录）
                if (currentTime >= aiTiming && aiActualTimes.size == currentTargetIndex) {
                    aiActualTimes.add(currentTime)
                    val aiError = abs(currentTime - targetTime)
                    aiTotalError += aiError

                    // 立即显示 AI 结果
                    currentRoundResult = "AI 已完成，误差：$aiError MS"
                }
            }
        }*/

        /*// AI 对战逻辑增强
        LaunchedEffect(timerState, currentTargetIndex, currentTime) {
            if (timerState == TimerState.RUNNING && !gameFinished) {
                val targetTime = expectedTimes[currentTargetIndex]

                // AI 智能计算时机
                val aiTiming = aiPlayer.calculateAITiming(targetTime, currentTime)

                // 模拟 AI 点击（在实际时间到达时记录）

                    aiActualTimes.add(currentTime)
                    val aiError = abs(currentTime - targetTime)
                    aiTotalError += aiError

                    // 显示 AI 结果
                    currentRoundResult = "AI 已完成，误差：$aiError MS"
                    // AI 学习
                    aiPlayer.learnFromRound(targetTime, currentTime, currentTime)

            }
        }

        // 显示 AI 智能信息
        val aiStats = remember { mutableStateOf(aiPlayer.getAIStats()) }

        LaunchedEffect(aiPlayer.historicalData.size) {
            aiStats.value = aiPlayer.getAIStats()
        }*/

        // 每轮开始前预测 AI 点击时间
        LaunchedEffect(currentTargetIndex, timerState) {
            if (timerState == TimerState.RUNNING && !gameFinished) {
                val targetTime = expectedTimes[currentTargetIndex]
                aiPredictedTiming = aiPlayer.calculateAITiming(targetTime, currentTime)
            }
        }

        // 监控 AI 点击时机
        LaunchedEffect(currentTime, timerState, currentTargetIndex) {
            if (timerState == TimerState.RUNNING &&
                !gameFinished &&
                aiActualTimes.size == currentTargetIndex &&
                currentTime >= aiPredictedTiming
            ) {

                // AI 点击
                aiActualTimes.add(currentTime)
                val targetTime = expectedTimes[currentTargetIndex]
                val aiError = abs(currentTime - targetTime)
                aiTotalError += aiError

                // 显示 AI 结果
                currentRoundResult = "AI 已完成，误差：$aiError MS"
            }
        }

        /*// 处理暂停事件（计算误差）
        LaunchedEffect(timerState) {
            if (timerState == TimerState.PAUSED) {
                // 计算当前误差（实际时间 - 目标时间）
                playerActualTimes.add(currentTime)
                val error = abs(currentTime - expectedTimes[currentTargetIndex])
                totalError += error
                XToast.showText("误差：${error} MS")

                // 移动到下一个时间点
                if (currentTargetIndex < expectedTimes.size - 1) {
                    currentTargetIndex++
                } else {
                    // 所有时间点已完成
                    gameFinished = true
                }
            }
        }*/

        /*// 处理玩家暂停事件（计算误差）
        LaunchedEffect(timerState) {
            if (timerState == TimerState.PAUSED && !gameFinished) {
                // 记录玩家时间
                playerActualTimes.add(currentTime)
                val targetTime = expectedTimes[currentTargetIndex]
                val playerError = abs(currentTime - targetTime)
                playerTotalError += playerError

                val aiTime: Long
                // 获取 AI 本轮时间（如果 AI 已经点击）
                if (aiActualTimes.size > currentTargetIndex) {
                    aiTime = aiActualTimes[currentTargetIndex]
                } else {
                    // AI 未点击，使用目标时间（视为 AI 失误）
                    aiTime = targetTime + 1000
                    aiTotalError += 1000
                }

                // AI 学习
                aiPlayer.learnFromRound(targetTime, currentTime, aiTime)

                // 显示本轮结果对比
                val aiError = abs(aiTime - targetTime)
                currentRoundResult = when {
                    playerError < aiError -> "本轮你赢了！（$playerError VS $aiError MS）"
                    playerError > aiError -> "本轮 AI 赢了！（$playerError VS $aiError MS）"
                    else -> "本轮平局！（$playerError MS）"
                }

                XToast.showText("玩家误差：${playerError} MS")

                // 移动到下一个时间点
                if (currentTargetIndex < expectedTimes.size - 1) {
                    currentTargetIndex++
                    currentRoundResult = "" // 清空当前轮次结果
                } else {
                    // 所有时间点已完成
                    gameFinished = true
                    // 显示最终结果
                    currentRoundResult = when {
                        playerTotalError < aiTotalError -> "你获胜！"
                        playerTotalError > aiTotalError -> "AI 获胜！"
                        else -> "平局！"
                    }
                }
            }
        }*/

        // 处理玩家暂停事件
        LaunchedEffect(timerState) {
            if (timerState == TimerState.PAUSED && !gameFinished) {
                // 记录玩家时间
                playerActualTimes.add(currentTime)
                val targetTime = expectedTimes[currentTargetIndex]
                val playerError = abs(currentTime - targetTime)
                playerTotalError += playerError

                // 获取 AI 本轮时间
                val aiTime = if (aiActualTimes.size > currentTargetIndex) {
                    aiActualTimes[currentTargetIndex]
                } else {
                    // AI 未点击，使用目标时间+惩罚
                    targetTime + 1000L
                }

                // AI 学习
                aiPlayer.learnFromRound(targetTime, currentTime, aiTime)

                // 计算 AI 误差
                val aiError = abs(aiTime - targetTime)

                // 显示本轮结果
                currentRoundResult = when {
                    playerError < aiError -> "本轮你赢了！（$playerError VS $aiError MS）"
                    playerError > aiError -> "本轮 AI 赢了！（$playerError VS $aiError MS）"
                    else -> "本轮平局！（$playerError MS）"
                }

                XToast.showText("玩家误差：${playerError} MS")

                // 移动到下一个时间点或结束游戏
                if (currentTargetIndex < expectedTimes.size - 1) {
                    currentTargetIndex++
                    currentRoundResult = ""
                } else {
                    gameFinished = true
                    currentRoundResult = when {
                        playerTotalError < aiTotalError -> "你获胜！"
                        playerTotalError > aiTotalError -> "AI 获胜！"
                        else -> "平局！"
                    }
                }
            }
        }

        // 处理重置事件
        /*val onReset: () -> Unit = {
            viewModel.resetTimer()
            currentTargetIndex = 0
            totalError = 0L
            actualTimes.clear()
            gameFinished = false
        }*/
        val onReset: () -> Unit = {
            viewModel.resetTimer()
            currentTargetIndex = 0
            playerTotalError = 0L
            aiTotalError = 0L
            playerActualTimes.clear()
            aiActualTimes.clear()
            currentRoundResult = ""
            gameFinished = false
            // aiPlayer.reset() // 重置 AI 学习状态
        }

        // 处理开始 / 暂停事件
        val onToggleRunning: () -> Unit = {
            if (!gameFinished) {
                viewModel.toggleIsRunning()
            }
        }

        XCard.Lively {
            Spacer(modifier = Modifier.height(10.dp))

            /*Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(ThemeColor, RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {*/
            Text(
                text = if (!gameFinished) {
                    "下一个目标：${expectedTimes[currentTargetIndex].toFloat().div(1000)}S"
                } else {
                    "游戏完成！"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            // }

            XDivider.Horizontal(space = 10.dp)

            StopWatchComponent(
                state = timerState,
                text = displayText,
                onToggleRunning = onToggleRunning,
                onReset = onReset,
                // 游戏完成时禁用开始按钮
                startButtonEnabled = !gameFinished,
            )

            XDivider.Horizontal(10.dp)

            Text(
                text = "玩家总误差：${playerTotalError} MS",
                fontSize = 16.sp,
                color = if (playerTotalError < 500) XContentColor.GREEN else XContentColor.RED,
                modifier = Modifier.clickVfx {
                    val intent =
                        Intent(this@ClassicPracticeActivity, ErrorDetailsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putLongArray("EXPECTED_TIMES", expectedTimes.toLongArray())
                    bundle.putLongArray("ACTUAL_TIMES", playerActualTimes.toLongArray())
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            )

            XDivider.Horizontal(10.dp)

            Text(
                text = "AI 总误差：${aiTotalError} MS",
                fontSize = 16.sp,
                color = if (aiTotalError < playerTotalError) XContentColor.GREEN else XContentColor.RED,
            )

            Text(
                text = "AI 难度：Lv.${aiPlayer.getAIDifficulty()}",
                fontSize = 16.sp,
                color = Color.Gray,
            )

            // 当前轮次结果显示
            if (currentRoundResult.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5f.dp,
                    color = XBorderColor.GRAY
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentRoundResult,
                    fontSize = 16.sp,
                    color = when {
                        currentRoundResult.contains("你获胜") -> XContentColor.GREEN
                        currentRoundResult.contains("AI 获胜") -> XContentColor.RED
                        else -> Color.Gray
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        /*// AI 智能信息面板
        AIIntelligencePanel(
            aiStats = aiStats.value,
            learningProgress = aiPlayer.getAILearningProgress()
        )*/

        Spacer(modifier = Modifier.height(50.dp))
    }
}

/*
// AI 智能信息面板组件
@Composable
fun AIIntelligencePanel(aiStats: IntelligentTimingAI.AIStats, learningProgress: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🤖 AI 智能分析",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("学习阶段:", fontWeight = FontWeight.Medium)
                Text(learningProgress, color = getLearningColor(learningProgress))
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("对战轮次：")
                Text("${aiStats.totalRounds} 轮")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("AI 平均误差：")
                Text("${"%.1f".format(aiStats.averageError)} MS")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("AI 胜率：")
                Text("${"%.1f".format(aiStats.winRate)}%")
            }

            // 难度可视化
            Text("AI 难度：Lv.${aiStats.currentDifficulty}")
            LinearProgressIndicator(
                progress = aiStats.currentDifficulty / 10f,
                modifier = Modifier.fillMaxWidth(),
                color = when {
                    aiStats.currentDifficulty >= 8 -> Color.Red
                    aiStats.currentDifficulty >= 5 -> Color.Yellow
                    else -> Color.Green
                }
            )
        }
    }
}

@Composable
fun getLearningColor(progress: String): Color {
    return when (progress) {
        "新手学习" -> Color.Gray
        "进阶适应" -> Color.Blue
        "高手模式" -> Color.Magenta
        "大师级 AI" -> Color.Red
        else -> Color.Black
    }
}*/