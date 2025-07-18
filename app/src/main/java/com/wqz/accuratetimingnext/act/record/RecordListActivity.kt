package com.wqz.accuratetimingnext.act.record

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.database.entity.GameRecord
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.color.TextFieldColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import com.wqz.accuratetimingnext.viewmodel.GameRecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * 游戏记录列表
 * Created by Wu Qizhen on 2025.7.17
 */
@AndroidEntryPoint
class RecordListActivity : ComponentActivity() {
    private val viewModel: GameRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.BreathingBackground(titleId = R.string.game_record) {
                RecordListScreen()
            }
        }
    }

    @Composable
    fun RecordListScreen() {
        // 收集ViewModel状态
        // val currentMonth by viewModel.currentMonth.collectAsState()
        val monthlyRecords by viewModel.monthlyRecords.collectAsState()
        // val uiState by viewModel.uiState.collectAsState()

        // 本地UI状态
        /*var inputMonth by remember {
            mutableStateOf(
                YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy.MM"))
            )
        }*/

        // 格式化日期
        // val dateFormat = DateTimeFormatter.ofPattern("yyyy年MM月", Locale.CHINA)
        // val currentDate = remember { mutableStateOf(LocalDate.now()) }

        // 月份选择器
        MonthSelector(
            // currentDate = currentDate.value,
            // dateFormat = dateFormat,
            // showJump = showJump,
            // inputMonth = inputMonth,
            /*onPrevClick = {
                currentDate.value = currentDate.value.minusMonths(1)
                viewModel.setMonth(YearMonth.from(currentDate.value))
            },*/
            /*onNextClick = {
                currentDate.value = currentDate.value.plusMonths(1)
                viewModel.setMonth(YearMonth.from(currentDate.value))
            },*/
            // onMonthClick = { showJump = !showJump },
            // onInputMonthChange = { inputMonth = it },
            /*onCurrentMonthClick = {
                currentDate.value = LocalDate.now()
                viewModel.setMonth(YearMonth.now())
                // showJump = false
            },*/
            /*onJumpClick = {
                if (isValidYearMonth(inputMonth)) {
                    val year = inputMonth.substring(0, 4).toInt()
                    val month = inputMonth.substring(5, 7).toInt()
                    currentDate.value = LocalDate.of(year, month, 1)
                    viewModel.setMonth(YearMonth.from(currentDate.value))
                    // showJump = false
                } else {
                    // 显示错误提示
                    XToast.showText("无效的日期格式")
                    // viewModel.setUiState(GameRecordUiState.Error("无效的日期格式"))
                }
            }*/
        )

        Spacer(modifier = Modifier.height(10.dp))

        when {
            monthlyRecords.isEmpty() -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                XCard.SurfaceCard {
                    monthlyRecords.forEach { record ->
                        key(record.id) {
                            RecordItem(record)

                            if (record != monthlyRecords.last()) {
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
    fun MonthSelector() {
        val currentDate = remember { mutableStateOf(LocalDate.now()) }
        var showJump by remember { mutableStateOf(false) }
        val dateFormat = DateTimeFormatter.ofPattern("yyyy年MM月", Locale.CHINA)
        var inputMonth by remember {
            mutableStateOf(
                YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy.MM"))
            )
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                IconButton(
                    onClick = {
                        currentDate.value = currentDate.value.minusMonths(1)
                        viewModel.setMonth(YearMonth.from(currentDate.value))
                    },
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "上个月",
                            tint = Color.White
                        )
                    },
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = dateFormat.format(currentDate.value),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        // .width(140.dp)
                        .clickVfx { showJump = !showJump },
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(5.dp))

                IconButton(
                    onClick = {
                        currentDate.value = currentDate.value.plusMonths(1)
                        viewModel.setMonth(YearMonth.from(currentDate.value))
                    },
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "下个月",
                            tint = Color.White
                        )
                    },
                    modifier = Modifier.size(25.dp)
                )
            }

            AnimatedVisibility(
                visible = showJump,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    XCard.LivelyCard {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = inputMonth,
                            onValueChange = { inputMonth = it },
                            colors = TextFieldColor.colors(),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.misans_regular)),
                                textAlign = TextAlign.Center
                            ),
                            placeholder = {
                                Text(
                                    text = "2025.05",
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            },
                            singleLine = true
                        )

                        Row {
                            XItem.Button(icon = R.drawable.ic_locate, text = "本月") {
                                currentDate.value = LocalDate.now()
                                viewModel.setMonth(YearMonth.now())
                                showJump = false
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            XItem.Button(icon = R.drawable.ic_jump, text = "跳转") {
                                if (isValidYearMonth(inputMonth)) {
                                    val year = inputMonth.substring(0, 4).toInt()
                                    val month = inputMonth.substring(5, 7).toInt()
                                    currentDate.value = LocalDate.of(year, month, 1)
                                    viewModel.setMonth(YearMonth.from(currentDate.value))
                                    showJump = false
                                } else {
                                    // 显示错误提示
                                    XToast.showText("无效的日期格式")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    /*@Composable
    fun MonthSelector(
        currentDate: LocalDate,
        dateFormat: DateTimeFormatter,
        // showJump: Boolean,
        inputMonth: String,
        onPrevClick: () -> Unit,
        onNextClick: () -> Unit,
        // onMonthClick: () -> Unit,
        onInputMonthChange: (String) -> Unit,
        onCurrentMonthClick: () -> Unit,
        onJumpClick: () -> Unit
    ) {
        var showJump by remember { mutableStateOf(false) }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                IconButton(
                    onClick = onPrevClick,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "上个月",
                            tint = Color.White
                        )
                    },
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = dateFormat.format(currentDate),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        // .width(140.dp)
                        .clickVfx { showJump = !showJump },
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(5.dp))

                IconButton(
                    onClick = onNextClick,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "下个月",
                            tint = Color.White
                        )
                    },
                    modifier = Modifier.size(25.dp)
                )
            }

            AnimatedVisibility(
                visible = showJump,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    XCard.LivelyCard {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = inputMonth,
                            onValueChange = onInputMonthChange,
                            colors = TextFieldColor.colors(),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.misans_regular)),
                                textAlign = TextAlign.Center
                            ),
                            placeholder = {
                                Text(
                                    text = "2025.05",
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            },
                            singleLine = true
                        )

                        Row {
                            XItem.Button(icon = R.drawable.ic_locate, text = "本月") {
                                onCurrentMonthClick
                                showJump = false
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            XItem.Button(icon = R.drawable.ic_jump, text = "跳转") {
                                onJumpClick
                                showJump = false
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }*/

    @Composable
    fun RecordItem(
        record: GameRecord
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
                .clickVfx(interactionSource) {
                    val intent = Intent(this, RecordDetailsActivity::class.java)
                    intent.putExtra("RECORD_ID", record.id)
                    startActivity(intent)
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.playerName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = contentColor,
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee()
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = record.timeName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = contentColor,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            color = ThemeColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_circle),
                        contentDescription = "记录 UID",
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = record.id.toString(),
                        fontSize = 12.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_eat_face),
                        contentDescription = "玩家 UID",
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = record.playerId.toString(),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = formatDate(record.gameTimestamp),
                    fontSize = 12.sp,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        /*Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(10.dp)
                .clickVfx(interactionSource)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(alignment = Alignment.TopStart)
            ) {
                Text(
                    text = record.playerName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            color = ThemeColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_circle),
                        contentDescription = "记录 ID",
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = record.id.toString(),
                        fontSize = 12.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_eat_face),
                        contentDescription = "玩家 ID",
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = record.playerId.toString(),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(alignment = Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = record.timeName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = contentColor,
                    modifier = Modifier
                        // .weight(1f)
                        .basicMarquee()
                )

                // Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = formatDate(record.gameTimestamp),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = Color.Gray
                )
            }
        }*/
    }

    // 辅助函数：验证年月格式
    private fun isValidYearMonth(input: String): Boolean {
        val pattern = Regex("""^\d{4}\.\d{2}$""")
        if (!pattern.matches(input)) return false

        return try {
            val year = input.substring(0, 4).toInt()
            val month = input.substring(5, 7).toInt()
            month in 1..12 && year > 2000 && year < 2100
        } catch (_: Exception) {
            false
        }
    }

    // 辅助函数：格式化时间戳为日期
    private fun formatDate(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss", Locale.getDefault())
        val date = java.time.Instant.ofEpochMilli(timestamp)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime()
        return formatter.format(date)
    }
}