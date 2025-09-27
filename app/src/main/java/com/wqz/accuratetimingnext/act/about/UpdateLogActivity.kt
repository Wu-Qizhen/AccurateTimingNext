package com.wqz.accuratetimingnext.act.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.about.data.UpdateLog
import com.wqz.accuratetimingnext.act.about.data.UpdateLogData
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBackgroundColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBorderColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground

/**
 * 更新日志
 * Created by Wu Qizhen on 2024.8.31
 * Refactored by Wu Qizhen on 2024.11.30
 */
class UpdateLogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(titleId = R.string.update_log) {
                UpdateLogScreen(
                    updateLogs = UpdateLogData.updateLogs
                )
            }
        }
    }

    @Composable
    fun UpdateLogScreen(
        updateLogs: List<UpdateLog>
    ) {
        updateLogs.forEach {
            key(it.version) {
                UpdateLogItem(it)
            }
        }

        Spacer(modifier = Modifier.height(47.dp))
    }

    @Composable
    fun UpdateLogItem(
        updateLog: UpdateLog
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) XBackgroundColor.GRAY_PRESSED else XBackgroundColor.GRAY
        val borderColors = XBorderColor.GRAY_GRADIENT
        val borderWidth = XWidth.BORDER_M
        var showDetails by remember { mutableStateOf(false) }

        Column {
            Spacer(modifier = Modifier.height(3.dp))

            Column(
                modifier = Modifier
                    .clickVfx(
                        interactionSource = interactionSource,
                        enabled = true,
                        onClick = {
                            showDetails = !showDetails
                        }
                    )
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(backgroundColor, RoundedCornerShape(10.dp))
                    .border(
                        width = borderWidth,
                        shape = RoundedCornerShape(10.dp),
                        brush = Brush.linearGradient(
                            borderColors,
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = updateLog.version,
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = updateLog.updateTime,
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                }

                AnimatedVisibility(
                    visible = showDetails,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            text = "版本介绍：",
                            fontSize = 14.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = updateLog.versionDesc,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            text = "更新内容：",
                            fontSize = 14.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = updateLog.updateLog,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(3.dp))
    }
}