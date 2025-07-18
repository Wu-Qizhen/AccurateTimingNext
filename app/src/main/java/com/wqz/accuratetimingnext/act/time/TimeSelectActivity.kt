package com.wqz.accuratetimingnext.act.time

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.ClassicPracticeActivity
import com.wqz.accuratetimingnext.act.game.util.Mode
import com.wqz.accuratetimingnext.act.player.PlayerSelectActivity
import com.wqz.accuratetimingnext.act.time.util.RandomUniqueTimeGenerator
import com.wqz.accuratetimingnext.database.entity.Time
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import com.wqz.accuratetimingnext.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 选择时间
 * Created by Wu Qizhen on 2025.7.6
 */
@AndroidEntryPoint
class TimeSelectActivity : ComponentActivity() {
    private val viewModel: TimeViewModel by viewModels()
    private var mode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mode = intent.getStringExtra("MODE")!!
        mode = if (mode == "") Mode.PRACTICE_MODE.name else mode

        setContent {
            XBackground.BreathingBackground(title = "选择时间") {
                TimeSelectScreen()
            }
        }
    }

    @Composable
    fun TimeSelectScreen() {
        val times by viewModel.times.collectAsStateWithLifecycle()

        if (mode == Mode.PRACTICE_MODE.name) {
            XItem.Bibutton(
                iconOne = R.drawable.ic_add,
                textOne = "添加",
                onClickOne = {
                    val intent = Intent(this, TimeAddActivity::class.java)
                    startActivity(intent)
                },
                iconTwo = R.drawable.ic_lightning,
                textTwo = "快速",
                onClickTwo = {
                    val randomTimes = RandomUniqueTimeGenerator.generateRandomUniqueTimes(0, 30000)
                    val times = randomTimes.toLongArray()
                    val intent = Intent(this, ClassicPracticeActivity::class.java)
                    intent.putExtra("TIME", times)
                    XToast.showText("已随机生成时间")
                    startActivity(intent)
                }
            )
        } else {
            XItem.Button(
                icon = R.drawable.ic_add,
                text = stringResource(id = R.string.add)
            ) {
                val intent = Intent(this, TimeAddActivity::class.java)
                startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when {
            times.isEmpty() -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                XCard.SurfaceCard {
                    times.forEach {
                        key(it.id) {
                            TimeItem(
                                time = it
                            )
                            if (it != times.last()) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    thickness = BorderWidth.DEFAULT_WIDTH,
                                    color = BorderColor.DEFAULT_GRAY
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    fun TimeItem(
        time: Time
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val contentColor =
            if (isPressed.value) Color.Gray else Color.White

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(10.dp)
                .clickVfx(
                    interactionSource = interactionSource,
                    enabled = true,
                    onClick = {
                        if (mode == Mode.PRACTICE_MODE.name) {
                            val intent = Intent(this, ClassicPracticeActivity::class.java)
                            intent.putExtra("TIME", time.expectedTimes.toLongArray())
                            startActivity(intent)
                        } else {
                            val intent =
                                Intent(this, PlayerSelectActivity::class.java)
                            intent.putExtra("TIME_ID", time.id)
                            intent.putExtra("TIME_NAME", time.timeName)
                            intent.putExtra("MODE", mode)
                            startActivity(intent)
                        }
                    }
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        Text(
                            text = time.timeName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = contentColor,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )

                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(
                                    color = ThemeColor,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = "${time.expectedTimes.size}",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }

                    Text(
                        text = time.expectedTimes.joinToString(", "),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
        }
    }
}