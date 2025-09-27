package com.wqz.accuratetimingnext.act.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.ui.StopWatchComponent
import com.wqz.accuratetimingnext.act.game.util.Mode
import com.wqz.accuratetimingnext.act.game.util.ModeTranslator
import com.wqz.accuratetimingnext.act.game.viewmodel.StopWatchViewModel
import com.wqz.accuratetimingnext.act.game.viewmodel.TimerState
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XContentColor
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XCard
import com.wqz.accuratetimingnext.aethex.matrix.ui.XDivider
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem
import com.wqz.accuratetimingnext.aethex.matrix.ui.XToast
import com.wqz.accuratetimingnext.database.entity.GameRecord
import com.wqz.accuratetimingnext.viewmodel.GameRecordViewModel
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import com.wqz.accuratetimingnext.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.random.Random

/**
 * 反向模式
 * Created by Wu Qizhen on 2025.7.16
 */
@AndroidEntryPoint
class ReverseChallengeActivity : ComponentActivity() {
    private var timeId: Int = -1
    private var playerId = -1
    private val reverseTimePoint = Random.nextInt(10, 60)
    private val reverseTime = reverseTimePoint.times(1000L)
    private val playerViewModel: PlayerViewModel by viewModels()
    private val timeViewModel: TimeViewModel by viewModels()
    private val gameRecordViewModel: GameRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(
                titleId = R.string.reverse_challenge
            ) {
                CompositionLocalProvider(LocalLifecycleOwner provides this) {
                    val viewModel: StopWatchViewModel = viewModel()

                    timeId = intent.getIntExtra("TIME_ID", -1)
                    playerId = intent.getIntExtra("PLAYER_ID", -1)

                    if (timeId == -1 || playerId == -1) {
                        XToast.showText("加载失败")
                        finish()
                        return@CompositionLocalProvider
                    }

                    ChallengeScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    @Composable
    fun ReverseScreen(onSkip: () -> Unit) {
        XCard.Lively(10) {
            Text(
                text = "记住反向时间点：",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Text(
                text = "${reverseTimePoint}S",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(10.dp))

            XItem.Button(icon = R.drawable.ic_star, "记好了", onClick = onSkip)
        }
    }

    @Composable
    fun ChallengeScreen(
        viewModel: StopWatchViewModel
    ) {
        // 添加记忆屏幕状态
        var showReverseScreen by remember { mutableStateOf(true) }
        // 当前目标时间点索引
        var currentTargetIndex by remember { mutableIntStateOf(0) }
        // 总误差
        var totalError by remember { mutableLongStateOf(0L) }
        // 游戏完成状态
        var gameFinished by remember { mutableStateOf(false) }
        var recordSaved by remember { mutableStateOf(false) }
        val actualTimes = remember { mutableListOf<Long>() }
        val errorTimes = remember { mutableListOf<Long>() }
        // 收集状态
        val timerState by viewModel.timerState.collectAsStateWithLifecycle()
        val currentTime by viewModel.currentTimeMillis.collectAsStateWithLifecycle()
        val displayText by viewModel.stopWatchText.collectAsStateWithLifecycle()

        // 数据加载
        LaunchedEffect(playerId, timeId) {
            playerViewModel.getById(playerId)
            timeViewModel.getById(timeId)
        }
        val player by playerViewModel.currentPlayer.collectAsStateWithLifecycle()
        val time by timeViewModel.currentTime.collectAsStateWithLifecycle()
        val expectedTimes: List<Long> = time?.expectedTimes ?: listOf(404L)

        // 加载状态处理
        if (player == null || time == null) {
            // 显示加载状态
            Image(
                painter = painterResource(id = R.drawable.ic_no_data),
                contentDescription = "无数据",
                modifier = Modifier
                    .size(100.dp)
            )
            return
        }

        // 根据状态显示不同屏幕
        if (showReverseScreen) {
            ReverseScreen(
                onSkip = { showReverseScreen = false }
            )
            return  // 显示记忆屏幕时提前返回
        }

        // 处理暂停事件（计算误差）
        LaunchedEffect(timerState) {
            if (timerState == TimerState.PAUSED) {
                // 计算当前误差（实际时间 - 目标时间）
                actualTimes.add(currentTime)
                val error = abs(currentTime - expectedTimes[currentTargetIndex])
                totalError += error
                errorTimes.add(error)  // 存储单个误差
                XToast.showText("误差：${error} MS")

                // 移动到下一个时间点
                if (currentTargetIndex < expectedTimes.size - 1) {
                    currentTargetIndex++
                } else {
                    // 所有时间点已完成
                    currentTargetIndex++
                    gameFinished = true
                }
            }
        }

        // 游戏结束时保存记录
        LaunchedEffect(gameFinished) {
            if (gameFinished && !recordSaved && player != null && time != null) {
                // 确保有足够的数据
                if (actualTimes.size == expectedTimes.size && errorTimes.size == expectedTimes.size) {
                    val index = expectedTimes.size - 4

                    player?.let { currentPlayer ->
                        // 计算平均误差（安全处理除零情况）
                        val averageError = totalError / actualTimes.size

                        // 创建数组的副本（避免修改原始引用）
                        val bestRecords = currentPlayer.bestRecords.copyOf()
                        val maximumAverageErrors = currentPlayer.maximumAverageErrors.copyOf()
                        val minimumAverageErrors = currentPlayer.minimumAverageErrors.copyOf()

                        // 更新 bestRecords：如果当前值为 -1，直接更新；否则仅当 totalError 更优时更新
                        if (index in bestRecords.indices) {
                            if (bestRecords[index] == -1 || totalError < bestRecords[index]) {
                                bestRecords[index] = totalError.toInt()
                            }
                        }

                        // 更新 maximumAverageErrors：如果当前值为 -1，直接更新；否则仅当 averageError 更大时更新
                        if (index in maximumAverageErrors.indices) {
                            if (maximumAverageErrors[index] == -1 || averageError > maximumAverageErrors[index]) {
                                maximumAverageErrors[index] = averageError.toInt()
                            }
                        }

                        // 更新 minimumAverageErrors：如果当前值为 -1，直接更新；否则仅当 averageError 更小时更新
                        if (index in minimumAverageErrors.indices) {
                            if (minimumAverageErrors[index] == -1 || averageError < minimumAverageErrors[index]) {
                                minimumAverageErrors[index] = averageError.toInt()
                            }
                        }

                        // 更新玩家数据
                        playerViewModel.update(
                            currentPlayer.copy(
                                numberOfCompetitions = currentPlayer.numberOfCompetitions + 1,
                                accumulationNumber = currentPlayer.accumulationNumber + actualTimes.size,
                                accumulationError = currentPlayer.accumulationError + totalError,
                                accumulationTime = currentPlayer.accumulationTime + actualTimes.last(),
                                bestRecords = bestRecords,
                                maximumAverageErrors = maximumAverageErrors,
                                minimumAverageErrors = minimumAverageErrors
                            )
                        )
                    } ?: run {
                        XToast.showText("加载失败")
                    }

                    gameRecordViewModel.insert(
                        gameRecord = GameRecord(
                            playerId = playerId,
                            playerName = player!!.playerName,
                            timeId = timeId,
                            timeName = time!!.timeName,
                            expectedTimes = expectedTimes,
                            actualTimes = actualTimes,
                            errorTimes = errorTimes,
                            totalError = totalError,
                            gameTimestamp = System.currentTimeMillis(),
                            gameRound = player!!.numberOfCompetitions,
                            gameTag = ModeTranslator.getChineseName(mode = Mode.REVERSE_MODE)
                        )
                    )
                    XToast.showText("记录已保存")
                    recordSaved = true
                } else {
                    XToast.showText("保存失败：数据不完整")
                }
            }
        }

        // 处理重置事件
        val onReset: () -> Unit = {
            viewModel.resetTimer()
            currentTargetIndex = 0
            totalError = 0L
            actualTimes.clear()
            gameFinished = false
        }

        // 处理开始/暂停事件
        val onToggleRunning: () -> Unit = {
            if (!gameFinished) {
                viewModel.toggleIsRunning()
            }
        }

        XCard.Lively {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (!gameFinished) {
                    "下一个目标：${
                        (reverseTime - expectedTimes[currentTargetIndex]).toFloat().div(1000)
                    }S"
                } else {
                    "游戏完成！"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            XDivider.Horizontal()

            Spacer(modifier = Modifier.height(10.dp))

            StopWatchComponent(
                state = timerState,
                text = displayText,
                onToggleRunning = onToggleRunning,
                onReset = onReset,
                // 游戏完成时禁用开始按钮
                startButtonEnabled = !gameFinished,
            )

            Spacer(modifier = Modifier.height(10.dp))

            XDivider.Horizontal()

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "总误差：${totalError} MS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalError < 500) XContentColor.GREEN else XContentColor.RED,
                modifier = Modifier.clickVfx {
                    val intent =
                        Intent(this@ReverseChallengeActivity, ErrorDetailsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putLongArray(
                        "EXPECTED_TIMES",
                        expectedTimes.subList(0, currentTargetIndex).toLongArray()
                    )
                    bundle.putLongArray(
                        "ACTUAL_TIMES",
                        actualTimes.subList(0, currentTargetIndex).toLongArray()
                    )
                    bundle.putInt("PLAYER_ID", playerId)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}