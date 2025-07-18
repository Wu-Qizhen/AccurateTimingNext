package com.wqz.accuratetimingnext.act.record

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import com.wqz.accuratetimingnext.viewmodel.GameRecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * 记录详情
 * Created by Wu Qizhen on 2025.7.18
 */
@AndroidEntryPoint
class RecordDetailsActivity : ComponentActivity() {
    private val viewModel: GameRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordId = intent.getIntExtra("RECORD_ID", -1)

        setContent {
            XBackground.BreathingBackground(title = "玩家详情") {
                RecordDetailsScreen(
                    recordId = recordId
                )
            }
        }
    }

    @Composable
    fun RecordDetailsScreen(
        recordId: Int
    ) {
        if (recordId == -1) {
            LaunchedEffect(Unit) {
                XToast.showText("加载失败")
                finish()
            }
            return  // 关键：直接返回不继续渲染
        }

        LaunchedEffect(recordId) {
            viewModel.getById(recordId)
        }

        val record by viewModel.currentGameRecord.collectAsStateWithLifecycle()

        when {
            record == null -> {
                // 显示加载状态
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                val currentRecord = record!!

                XCard.LivelyCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "记录 UID：",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )

                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .background(
                                        color = ThemeColor,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(
                                    text = currentRecord.id.toString(),
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    fontSize = 12.sp,
                                    maxLines = 1
                                )
                            }
                        }

                        /*Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "玩家信息：",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .basicMarquee()
                            ) {
                                Text(
                                    text = currentRecord.playerName,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .background(
                                            color = ThemeColor,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = currentRecord.playerId.toString(),
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 12.sp,
                                        maxLines = 1
                                    )
                                }
                            }
                        }*/

                        DataBarWithId(
                            title = "玩家信息",
                            data = currentRecord.playerName,
                            id = currentRecord.playerId
                        )

                        DataBarWithId(
                            title = "时间信息",
                            data = currentRecord.timeName,
                            id = currentRecord.timeId
                        )

                        /*Text(
                            text = "游戏局次：${currentRecord.gameRound}",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee()
                        )

                        Text(
                            text = "游戏时间：${formatDate(currentRecord.gameTimestamp)}",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee()
                        )

                        Text(
                            text = "游戏类型：${currentRecord.gameTag}",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee()
                        )

                        Text(
                            text = "总误差：${currentRecord.totalError} MS",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee()
                        )*/

                        DataBar(title = "游戏局次", data = currentRecord.gameRound.toString())

                        DataBar(title = "游戏时间", data = formatDate(currentRecord.gameTimestamp))

                        DataBar(title = "游戏类型", data = currentRecord.gameTag)

                        DataBar(title = "总误差", data = "${currentRecord.totalError} MS")
                    }

                    HorizontalDivider(
                        thickness = BorderWidth.DEFAULT_WIDTH,
                        color = BorderColor.DEFAULT_GRAY
                    )

                    ErrorDetailsComponent(
                        expectedTimes = currentRecord.expectedTimes,
                        actualTimes = currentRecord.actualTimes,
                        errorTimes = currentRecord.errorTimes
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    fun DataBarWithId(
        title: String,
        data: String,
        id: Int
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$title：",
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .basicMarquee()
            ) {
                Text(
                    text = data,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(5.dp))

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            color = ThemeColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = id.toString(),
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }

    @Composable
    fun DataBar(
        title: String,
        data: String
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$title：",
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Text(
                text = data,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .basicMarquee()
            )
        }
    }

    @Composable
    fun ErrorDetailsComponent(
        expectedTimes: List<Long>,
        actualTimes: List<Long>,
        errorTimes: List<Long>
    ) {
        /*val errorTimes = actualTimes.mapIndexed { index, actualTime ->
            abs(actualTime - expectedTimes[index])
        }*/

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "期望值", fontWeight = FontWeight.Bold)
                // 换行符拼接成字符串
                Text(
                    text = expectedTimes.joinToString("\n"),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "实际值", fontWeight = FontWeight.Bold)
                Text(
                    text = actualTimes.joinToString("\n"),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "误差", fontWeight = FontWeight.Bold)
                Text(
                    text = errorTimes.joinToString("\n"),
                    textAlign = TextAlign.Center
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = BorderWidth.DEFAULT_WIDTH,
            color = BorderColor.DEFAULT_GRAY
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "平均误差：${
                if (actualTimes.isNotEmpty()) errorTimes.average().toInt() else "--"
            } MS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

    // 辅助函数：格式化时间戳为日期
    private fun formatDate(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = java.time.Instant.ofEpochMilli(timestamp)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime()
        return formatter.format(date)
    }
}