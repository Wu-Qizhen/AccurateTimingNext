package com.wqz.accuratetimingnext.act.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import kotlin.math.abs

/**
 * 误差详情
 * Created by Wu Qizhen on 2025.7.15
 */
class ErrorDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val expectedTimes = intent.getLongArrayExtra("EXPECTED_TIMES")?.toList() ?: listOf()
        val actualTimes = intent.getLongArrayExtra("ACTUAL_TIMES")?.toList() ?: listOf()
        val playerId = intent.getIntExtra("PLAYER_ID", -1)

        setContent {
            XBackground.BreathingBackground("误差详情") {
                ErrorDetailsScreen(
                    expectedTimes = expectedTimes,
                    actualTimes = actualTimes,
                    playerId = playerId
                )
            }
        }
    }

    @Composable
    fun ErrorDetailsScreen(
        expectedTimes: List<Long>,
        actualTimes: List<Long>,
        playerId: Int = -1
    ) {
        val errorTimes = actualTimes.mapIndexed { index, actualTime ->
            abs(actualTime - expectedTimes[index])
        }

        XCard.LivelyCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
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

            // Spacer(modifier = Modifier.height(10.dp))

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

            if (playerId != -1) {
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = BorderWidth.DEFAULT_WIDTH,
                    color = BorderColor.DEFAULT_GRAY
                )

                // Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        // .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "玩家 UID：",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
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
                            text = playerId.toString(),
                            modifier = Modifier
                                .align(Alignment.Center),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}