package com.wqz.accuratetimingnext.act.rank

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.rank.ui.RankItem
import com.wqz.accuratetimingnext.act.rank.util.Sort
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 排行榜列表
 * Created by Wu Qizhen on 2025.7.17
 */
@AndroidEntryPoint
class RankFilterListActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()
    private var sort: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sort = intent.getStringExtra("SORT")!!
        sort = if (sort == "") Sort.NUMBER_OF_GAME.name else sort

        setContent {
            XBackground.BreathingBackground(titleId = R.string.ranking_list) {
                RankListScreen()
            }
        }
    }

    @Composable
    fun RankListScreen() {
        val players by viewModel.players.collectAsStateWithLifecycle()
        var showFilter by remember { mutableStateOf(false) }
        var currentNumber by remember { mutableIntStateOf(4) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    currentNumber = if (currentNumber > 4) currentNumber - 1 else 4
                },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "减少",
                        tint = Color.White
                    )
                },
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "时数：$currentNumber",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(110.dp)
                    .clickVfx {
                        showFilter = !showFilter
                    },
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(
                onClick = {
                    currentNumber = if (currentNumber < 10) currentNumber + 1 else 10
                },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "增加",
                        tint = Color.White
                    )
                },
                modifier = Modifier.size(25.dp)
            )
        }

        AnimatedVisibility(
            visible = showFilter,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))

                XCard.LivelyCard(
                    10
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        XItem.Button(text = "4") {
                            currentNumber = 4
                            showFilter = false
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        XItem.Button(text = "5") {
                            currentNumber = 5
                            showFilter = false
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        XItem.Button(text = "6") {
                            currentNumber = 6
                            showFilter = false
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        XItem.Button(text = "7") {
                            currentNumber = 7
                            showFilter = false
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        XItem.Button(text = "8") {
                            currentNumber = 8
                            showFilter = false
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        XItem.Button(text = "9") {
                            currentNumber = 9
                            showFilter = false
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        XItem.Button(text = "10") {
                            currentNumber = 10
                            showFilter = false
                        }
                    }

                    // Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when {
            players.isEmpty() -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "无数据",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            else -> {
                XCard.SurfaceCard {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterStart),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.width(35.dp)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "排名",
                                    // fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "玩家",
                                // fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }

                        Text(
                            text = "战绩",
                            modifier = Modifier
                                .width(35.dp)
                                .align(Alignment.CenterEnd),
                            // fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = BorderWidth.DEFAULT_WIDTH,
                        color = BorderColor.DEFAULT_GRAY
                    )

                    players.sortedByDescending { player ->
                        when (sort) {
                            Sort.BEST_RECORD.name -> player.bestRecords[currentNumber - 4]
                            Sort.MAXIMUM_AVERAGE_ERROR.name -> player.maximumAverageErrors[currentNumber - 4]
                            Sort.MINIMUM_AVERAGE_ERROR.name -> player.minimumAverageErrors[currentNumber - 4]
                            else -> player.bestRecords[currentNumber - 4]
                        }
                    }
                        .forEach { player ->
                            key(player.id) {
                                RankItem(
                                    rank = players.indexOf(player) + 1,
                                    playerId = player.id,
                                    playerName = player.playerName,
                                    data = when (sort) {
                                        Sort.BEST_RECORD.name -> player.bestRecords[currentNumber - 4]
                                        Sort.MAXIMUM_AVERAGE_ERROR.name -> player.maximumAverageErrors[currentNumber - 4]
                                        Sort.MINIMUM_AVERAGE_ERROR.name -> player.minimumAverageErrors[currentNumber - 4]
                                        else -> player.bestRecords[currentNumber - 4]
                                    }
                                )

                                if (player != players.last()) {
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
}