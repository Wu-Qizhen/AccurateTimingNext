package com.wqz.accuratetimingnext.act.time

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.time.util.TimeFormat.formatMilliseconds
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.color.TextFieldColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.property.ButtonCategory
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import com.wqz.accuratetimingnext.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 时间详情
 * Created by Wu Qizhen on 2025.7.4
 */
@AndroidEntryPoint
class TimeDetailsActivity : ComponentActivity() {
    private val viewModel: TimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timeId = intent.getIntExtra("TIME_ID", -1)

        setContent {
            XBackground.BreathingBackground(title = "时间详情") {
                TimeDetailsScreen(
                    timeId = timeId
                )
            }
        }
    }

    @Composable
    fun TimeDetailsScreen(
        timeId: Int
    ) {
        /*if (timeId == -1) {
            XToast.showText("请选择时间")
            finish()
        }

        LaunchedEffect(timeId) {
            if (timeId != -1) {
                viewModel.getById(timeId)
            }
        }*/

        // 1. 处理无效ID
        if (timeId == -1) {
            LaunchedEffect(Unit) {
                XToast.showText("加载失败")
                // delay(1000)  // 确保提示可见
                finish()
            }
            return  // 关键：直接返回不继续渲染
        }

        // 2. 触发数据加载
        LaunchedEffect(timeId) {
            viewModel.getById(timeId)
        }

        val time by viewModel.currentTime.collectAsStateWithLifecycle()

        // 4. 根据状态显示不同UI
        when {
            time == null -> {
                // 显示加载状态
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                // 5. 安全访问非空数据
                val currentTime = time!!

                // 6. 使用remember保存状态（使用copy防止原始数据被修改）
                var timeName by remember {
                    mutableStateOf(currentTime.timeName)
                }

                /*if (time == null) {
                    XToast.showText("请选择时间")
                    finish()
                }

                var timeName by remember { mutableStateOf(time!!.timeName) }*/
                // val times by remember { mutableStateOf(time!!.expectedTimes.toString()) }
                var deleteConfirm by remember { mutableIntStateOf(0) }

                XCard.LivelyCard {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = timeName,
                        onValueChange = { timeName = it },
                        colors = TextFieldColor.colors(),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.misans_regular))
                        ),
                        label = {
                            Text(
                                text = "时间名",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        maxLines = 1
                    )

                    HorizontalDivider(
                        thickness = BorderWidth.DEFAULT_WIDTH,
                        color = BorderColor.DEFAULT_GRAY
                    )

                    // Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                        // verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "时间：\n（秒）",
                            fontWeight = FontWeight.Bold
                        )

                        Column {
                            currentTime.expectedTimes.forEachIndexed { index, it ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(25.dp)
                                            .background(
                                                color = ThemeColor,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                    ) {
                                        Text(
                                            text = (index + 1).toString(),
                                            modifier = Modifier
                                                .align(Alignment.Center),
                                            fontSize = 10.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = formatMilliseconds(it)
                                    )
                                }
                            }
                        }
                    }

                    // Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        thickness = BorderWidth.DEFAULT_WIDTH,
                        color = BorderColor.DEFAULT_GRAY
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UID：",
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
                                text = currentTime.id.toString(),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    XItem.Button(
                        icon = R.drawable.ic_delete,
                        text = stringResource(id = R.string.delete),
                        color = ButtonCategory.WARNING_BUTTON
                    ) {
                        deleteConfirm++
                        if (deleteConfirm > 2) {
                            viewModel.deleteById(timeId)
                            XToast.showText(
                                this@TimeDetailsActivity,
                                R.string.deleted
                            )
                            finish()
                        } else {
                            XToast.showText("再按 ${3 - deleteConfirm} 次即可删除")
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    XItem.Button(
                        icon = R.drawable.ic_confirm,
                        text = stringResource(id = R.string.save),
                        color = ButtonCategory.SAFE_BUTTON
                    ) {
                        val title = timeName.trim().replace("\n", "")
                        // val expectedTimes = parseAndConvertTimes(times)
                        if (title.isNotEmpty()) {
                            viewModel.updateTimeName(timeId, title)
                            XToast.showText(this@TimeDetailsActivity, R.string.added)
                            finish()
                        } else {
                            XToast.showText("时间名不能为空")
                        }
                    }

                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}