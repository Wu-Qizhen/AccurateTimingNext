package com.wqz.accuratetimingnext.act.time

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.time.util.RandomUniqueTimeGenerator
import com.wqz.accuratetimingnext.act.time.util.TimeConverters.parseAndConvertTimes
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XTextFieldColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XCard
import com.wqz.accuratetimingnext.aethex.matrix.ui.XDivider
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem
import com.wqz.accuratetimingnext.aethex.matrix.ui.XToast
import com.wqz.accuratetimingnext.viewmodel.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 添加时间
 * Created by Wu Qizhen on 2025.7.4
 */
@AndroidEntryPoint
class TimeAddActivity : ComponentActivity() {
    private val viewModel: TimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(title = "添加时间") {
                TimeAddScreen()
            }
        }
    }

    @Composable
    fun TimeAddScreen() {
        val context = LocalContext.current
        var times by remember { mutableStateOf("") }
        var timeName by remember { mutableStateOf("") }
        var count by remember { mutableStateOf("") }

        XCard.Lively {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = timeName,
                onValueChange = { timeName = it },
                colors = XTextFieldColor.textFieldSurface(),
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

            XDivider.Horizontal()

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = times,
                onValueChange = { times = it },
                colors = XTextFieldColor.textFieldSurface(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.misans_regular))
                ),
                label = {
                    Text(
                        text = "时间",
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            XDivider.Horizontal()

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = count,
                onValueChange = { count = it },
                colors = XTextFieldColor.textFieldSurface(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.misans_regular))
                ),
                label = {
                    Text(
                        text = "随机个数",
                        fontWeight = FontWeight.Bold
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            XDivider.Horizontal()

            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "时间格式与规则：",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(R.string.time_rules),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "* 创建后仅可更改名称",
                    fontSize = 12.sp,
                    color = XThemeColor.NORMAL,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        XItem.Bibutton(
            iconOne = R.drawable.ic_star,
            textOne = "随机",
            onClickOne = {
                if (count.isEmpty() || count.toInt() == 0) {
                    val randomTimes = RandomUniqueTimeGenerator.generateRandomUniqueTimes(0, 30000)
                    times = randomTimes.joinToString("\n")
                    XToast.showText("已随机生成时间")
                } else if (count.toInt() in 4..10) {
                    val randomTimes =
                        RandomUniqueTimeGenerator.generateRandomUniqueTimes(count.toInt(), 30000)
                    times = randomTimes.joinToString("\n")
                    XToast.showText("已随机生成时间")
                } else {
                    XToast.showText("请输入正确的随机个数")
                }
            },
            iconTwo = R.drawable.ic_add,
            textTwo = "添加",
            onClickTwo = {
                val title = timeName.trim().replace("\n", "")
                val expectedTimes = parseAndConvertTimes(times)
                if (title.isNotEmpty() && expectedTimes.isNotEmpty()) {
                    if (expectedTimes.size >= 4 && expectedTimes.size <= 10) {
                        viewModel.insert(title, expectedTimes)
                        XToast.showText(context, R.string.added)
                        finish()
                    } else {
                        XToast.showText("时间名或时间格式错误")
                    }
                } else {
                    XToast.showText("时间名或时间不能为空")
                }
            }
        )

        /*Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Button(icon = R.drawable.ic_star, text = "随机") {
                if (count.isEmpty() || count.toInt() == 0) {
                    val randomTimes = RandomUniqueTimeGenerator.generateRandomUniqueTimes(0, 30000)
                    times = randomTimes.joinToString("\n")
                    XToast.showText("已随机生成时间")
                } else if (count.toInt() in 4..10) {
                    val randomTimes =
                        RandomUniqueTimeGenerator.generateRandomUniqueTimes(count.toInt(), 30000)
                    times = randomTimes.joinToString("\n")
                    XToast.showText("已随机生成时间")
                } else {
                    XToast.showText("请输入正确的随机个数")
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            XItem.Button(icon = R.drawable.ic_add, text = "添加") {
                val title = timeName.trim().replace("\n", "")
                val expectedTimes = parseAndConvertTimes(times)
                if (title.isNotEmpty() && expectedTimes.isNotEmpty()) {
                    if (expectedTimes.size >= 4 && expectedTimes.size <= 10) {
                        viewModel.insert(title, expectedTimes)
                        XToast.showText(context, R.string.added)
                        finish()
                    } else {
                        XToast.showText("时间名或时间格式错误")
                    }
                } else {
                    XToast.showText("时间名或时间不能为空")
                }
            }
        }*/

        Spacer(modifier = Modifier.height(50.dp))
    }
}