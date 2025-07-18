package com.wqz.accuratetimingnext.act.rank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.rank.ui.RankItem
import com.wqz.accuratetimingnext.act.rank.util.Sort
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 排行榜列表
 * Created by Wu Qizhen on 2025.7.17
 */
@AndroidEntryPoint
class RankListActivity : ComponentActivity() {
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
                            Sort.NUMBER_OF_GAME.name -> player.numberOfCompetitions
                            Sort.ACCUMULATION_NUMBER.name -> player.accumulationNumber
                            Sort.ACCUMULATION_ERROR.name -> player.accumulationError.toInt()
                            Sort.ACCUMULATION_TIME.name -> player.accumulationTime.toInt()
                            else -> player.numberOfCompetitions
                        }
                    }
                        .forEach { player ->
                            key(player.id) {
                                RankItem(
                                    rank = players.indexOf(player) + 1,
                                    playerId = player.id,
                                    playerName = player.playerName,
                                    data = when (sort) {
                                        Sort.NUMBER_OF_GAME.name -> player.numberOfCompetitions
                                        Sort.ACCUMULATION_NUMBER.name -> player.accumulationNumber
                                        Sort.ACCUMULATION_ERROR.name -> player.accumulationError.toInt()
                                        Sort.ACCUMULATION_TIME.name -> player.accumulationTime.toInt()
                                        else -> player.numberOfCompetitions
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