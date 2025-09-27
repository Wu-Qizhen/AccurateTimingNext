package com.wqz.accuratetimingnext.act.player

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.player.util.PlayerValidator
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XButtonColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XTextFieldColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XCard
import com.wqz.accuratetimingnext.aethex.matrix.ui.XDivider
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem
import com.wqz.accuratetimingnext.aethex.matrix.ui.XToast
import com.wqz.accuratetimingnext.database.entity.Player
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 玩家详情
 * Created by Wu Qizhen on 2025.7.4
 */
@AndroidEntryPoint
class PlayerDetailsActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerId = intent.getIntExtra("PLAYER_ID", -1)

        setContent {
            XBackground.Breathing(title = "玩家详情") {
                PlayerDetailsScreen(
                    playerId = playerId
                )
            }
        }
    }

    @Composable
    fun PlayerDetailsScreen(
        playerId: Int
    ) {
        if (playerId == -1) {
            LaunchedEffect(Unit) {
                XToast.showText("加载失败")
                // delay(1000)  // 确保提示可见
                finish()
            }
            return  // 关键：直接返回不继续渲染
        }

        LaunchedEffect(playerId) {
            viewModel.getById(playerId)
        }

        val player by viewModel.currentPlayer.collectAsStateWithLifecycle()

        when {
            player == null -> {
                // 显示加载状态
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                val currentPlayer = player!!

                var playerName by remember {
                    mutableStateOf(currentPlayer.playerName)
                }

                var deleteConfirm by remember { mutableIntStateOf(0) }

                XCard.Lively {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = playerName,
                        onValueChange = { playerName = it },
                        colors = XTextFieldColor.textFieldSurface(),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.misans_regular))
                        ),
                        label = {
                            Text(
                                text = "玩家名",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        maxLines = 1
                    )

                    XDivider.Horizontal()

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UID：",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )

                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(
                                    color = XThemeColor.NORMAL,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = currentPlayer.id.toString(),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        Text(
                            text = "游戏局数：${currentPlayer.numberOfCompetitions}",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )

                        Text(
                            text = "卡时次数：${currentPlayer.accumulationNumber}",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )

                        Text(
                            text = "累计误差：${currentPlayer.accumulationError} MS",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )

                        Text(
                            text = "累计时间：${currentPlayer.accumulationTime} MS",
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    XDivider.Horizontal(space = 10.dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "时数\n4\n5\n6\n7\n8\n9\n10",
                            fontWeight = FontWeight.Bold,
                            color = XThemeColor.NORMAL,
                            textAlign = TextAlign.Center
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "最佳",
                                fontWeight = FontWeight.Bold,
                                color = XThemeColor.NORMAL,
                                maxLines = 1
                            )

                            Text(
                                text = currentPlayer.bestRecords.toList()
                                    .joinToString("\n") { if (it == -1) "--" else it.toString() },
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "↑Avg",
                                fontWeight = FontWeight.Bold,
                                color = XThemeColor.NORMAL,
                                maxLines = 1
                            )

                            Text(
                                text = currentPlayer.maximumAverageErrors.toList()
                                    .joinToString("\n") { if (it == -1) "--" else it.toString() },
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "↓Avg",
                                fontWeight = FontWeight.Bold,
                                color = XThemeColor.NORMAL,
                                maxLines = 1
                            )

                            Text(
                                text = currentPlayer.minimumAverageErrors.toList()
                                    .joinToString("\n") { if (it == -1) "--" else it.toString() },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
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
                        color = XButtonColor.WARNING
                    ) {
                        deleteConfirm++
                        if (deleteConfirm > 2) {
                            viewModel.deleteById(playerId)
                            XToast.showText(
                                this@PlayerDetailsActivity,
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
                        color = XButtonColor.SAFE
                    ) {
                        val name = playerName.trim().replace("\n", "")
                        // val expectedPlayers = parseAndConvertPlayers(players)
                        if (PlayerValidator.isValidPlayerName(name)) {
                            viewModel.update(Player(playerId, name))
                            XToast.showText(this@PlayerDetailsActivity, R.string.added)
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