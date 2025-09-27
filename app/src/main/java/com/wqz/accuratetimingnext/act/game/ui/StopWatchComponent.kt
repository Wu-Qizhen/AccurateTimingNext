package com.wqz.accuratetimingnext.act.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.viewmodel.TimerState
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem

/**
 * 停止计时器组件
 * Created by Wu Qizhen on 2025.7.3
 */
@Composable
fun StopWatchComponent(
    state: TimerState,
    text: String,
    onToggleRunning: () -> Unit,
    onReset: () -> Unit,
    startButtonEnabled: Boolean = true // 新增开始按钮启用状态
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(R.font.digital_mono, FontWeight.Bold)),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Button(text = "重置", enabled = state == TimerState.PAUSED) {
                onReset()
            }

            Spacer(modifier = Modifier.width(10.dp))

            XItem.Button(
                text = if (state == TimerState.RUNNING) "暂停" else "开始",
                enabled = startButtonEnabled
            ) {
                onToggleRunning()
            }
        }
    }
}