package com.wqz.accuratetimingnext.act.player

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
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XCard
import com.wqz.accuratetimingnext.aethex.matrix.ui.XDivider
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem
import com.wqz.accuratetimingnext.database.entity.Player
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 玩家列表
 * Created by Wu Qizhen on 2025.7.4
 */
@AndroidEntryPoint
class PlayerListActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(title = "玩家管理") {
                PlayerListScreen()
            }
        }
    }

    @Composable
    fun PlayerListScreen() {
        val players by viewModel.players.collectAsStateWithLifecycle()

        XItem.Button(
            icon = R.drawable.ic_add,
            text = stringResource(id = R.string.add)
        ) {
            val intent = Intent(this, PlayerAddActivity::class.java)
            startActivity(intent)
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
                XCard.Surface {
                    players.forEach {
                        key(it.id) {
                            PlayerItem(
                                player = it
                            )
                            if (it != players.last()) {
                                XDivider.Horizontal()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    fun PlayerItem(
        player: Player
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
                        val intent =
                            Intent(this, PlayerDetailsActivity::class.java)
                        intent.putExtra("PLAYER_ID", player.id)
                        startActivity(intent)
                    }
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_player),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = player.playerName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(3.dp))

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
                            text = "${player.id}",
                            modifier = Modifier
                                .align(Alignment.Center),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}