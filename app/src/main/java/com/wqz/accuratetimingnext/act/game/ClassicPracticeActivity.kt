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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.ui.StopWatchComponent
import com.wqz.accuratetimingnext.act.game.viewmodel.StopWatchViewModel
import com.wqz.accuratetimingnext.act.game.viewmodel.TimerState
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.color.ContentColor
import kotlin.math.abs

/**
 * 经典模式
 * Created by Wu Qizhen on 2025.7.15
 */
class ClassicPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.BreathingBackground(
                titleId = R.string.practice_mode
            ) {
                CompositionLocalProvider(LocalLifecycleOwner provides this) {
                    val viewModel: StopWatchViewModel = viewModel()
                    val expectedTimes =
                        intent.getLongArrayExtra("TIME")?.toList() ?: listOf(404L)

                    ClassicPracticeScreen(
                        viewModel = viewModel,
                        expectedTimes = expectedTimes
                    )
                }
            }
        }
    }

    @Composable
    fun ClassicPracticeScreen(
        viewModel: StopWatchViewModel,
        expectedTimes: List<Long>
    ) {
        // 当前目标时间点索引
        var currentTargetIndex by remember { mutableIntStateOf(0) }
        // 总误差
        var totalError by remember { mutableLongStateOf(0L) }
        // 游戏完成状态
        var gameFinished by remember { mutableStateOf(false) }
        val actualTimes = remember { mutableListOf<Long>() }

        // 收集状态
        val timerState by viewModel.timerState.collectAsStateWithLifecycle()
        val currentTime by viewModel.currentTimeMillis.collectAsStateWithLifecycle()
        val displayText by viewModel.stopWatchText.collectAsStateWithLifecycle()

        // 处理暂停事件（计算误差）
        LaunchedEffect(timerState) {
            if (timerState == TimerState.PAUSED) {
                // 计算当前误差（实际时间 - 目标时间）
                actualTimes.add(currentTime)
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

        XCard.LivelyCard {
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

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5f.dp,
                color = BorderColor.DEFAULT_GRAY
            )

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

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5f.dp,
                color = BorderColor.DEFAULT_GRAY
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "总误差：${totalError} MS",
                fontSize = 16.sp,
                color = if (totalError < 500) ContentColor.DEFAULT_GREEN else ContentColor.DEFAULT_RED,
                modifier = Modifier.clickVfx {
                    val intent =
                        Intent(this@ClassicPracticeActivity, ErrorDetailsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putLongArray("EXPECTED_TIMES", expectedTimes.toLongArray())
                    bundle.putLongArray("ACTUAL_TIMES", actualTimes.toLongArray())
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}