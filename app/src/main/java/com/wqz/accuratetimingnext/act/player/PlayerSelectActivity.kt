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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.game.BlindChallengeActivity
import com.wqz.accuratetimingnext.act.game.ComputationalChallengeActivity
import com.wqz.accuratetimingnext.act.game.DisorderedMemoryChallengeActivity
import com.wqz.accuratetimingnext.act.game.MemoryChallengeActivity
import com.wqz.accuratetimingnext.act.game.RaceRecordActivity
import com.wqz.accuratetimingnext.act.game.ReverseChallengeActivity
import com.wqz.accuratetimingnext.act.game.util.Mode
import com.wqz.accuratetimingnext.database.entity.Player
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.ThemeColor
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 选择玩家
 * Created by Wu Qizhen on 2025.7.6
 */
@AndroidEntryPoint
class PlayerSelectActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()
    private lateinit var mode: Mode
    private var timeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val modeString = intent.getStringExtra("MODE")
        mode = try {
            enumValueOf<Mode>(modeString!!)
        } catch (_: IllegalArgumentException) {
            Mode.PRACTICE_MODE
        }

        timeId = intent.getIntExtra("TIME_ID", -1)
        val timeName = intent.getStringExtra("TIME_NAME")
        if (timeName == null || timeId < 0) {
            XToast.showText("请选择时间")
            finish()
            return
        }

        setContent {
            XBackground.BreathingBackground(title = "选择玩家") {
                PlayerSelectScreen(
                    timeName = timeName
                )
            }
        }
    }

    @Composable
    fun PlayerSelectScreen(
        timeName: String
    ) {
        val players by viewModel.players.collectAsStateWithLifecycle()

        Box(
            modifier = Modifier
                .clickVfx()
                .wrapContentWidth()
                .background(
                    color = ThemeColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = "时间：$timeName",
                // fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

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
                XCard.SurfaceCard {
                    players.forEach {
                        key(it.id) {
                            PlayerItem(
                                player = it
                            )
                            if (it != players.last()) {
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
                        var intent: Intent
                        when (mode) {
                            Mode.RACING_MODE -> {
                                intent = Intent(this, RaceRecordActivity::class.java)
                            }

                            Mode.BLIND_MODE -> {
                                intent = Intent(this, BlindChallengeActivity::class.java)
                            }

                            Mode.MEMORY_MODE -> {
                                intent = Intent(this, MemoryChallengeActivity::class.java)
                            }

                            Mode.REVERSE_MODE -> {
                                intent = Intent(this, ReverseChallengeActivity::class.java)
                            }

                            Mode.DISORDERED_MEMORY_MODE -> {
                                intent = Intent(this, DisorderedMemoryChallengeActivity::class.java)
                            }

                            Mode.COMPUTATIONAL_MODE -> {
                                intent = Intent(this, ComputationalChallengeActivity::class.java)
                            }

                            /*Mode.EXPERT_MODE -> {
                            }*/

                            else -> {
                                intent = Intent(this, RaceRecordActivity::class.java)
                            }
                        }
                        intent.putExtra("TIME_ID", timeId)
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
                                color = ThemeColor,
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