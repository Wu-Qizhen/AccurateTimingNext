package com.wqz.accuratetimingnext.act.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.player.util.PlayerValidator
import com.wqz.accuratetimingnext.ui.XBackground
import com.wqz.accuratetimingnext.ui.XCard
import com.wqz.accuratetimingnext.ui.XItem
import com.wqz.accuratetimingnext.ui.XToast
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.color.TextFieldColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 添加玩家
 * Created by Wu Qizhen on 2025.7.4
 */
@AndroidEntryPoint
class PlayerAddActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.BreathingBackground(title = "添加玩家") {
                PlayerAddScreen()
            }
        }
    }

    @Composable
    fun PlayerAddScreen() {
        val context = LocalContext.current
        var playerName by remember { mutableStateOf("") }

        XCard.LivelyCard {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = playerName,
                onValueChange = { playerName = it },
                colors = TextFieldColor.colors(),
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

            HorizontalDivider(
                thickness = BorderWidth.DEFAULT_WIDTH,
                color = BorderColor.DEFAULT_GRAY
            )

            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "玩家名命名规则：",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(R.string.player_rules),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        XItem.Button(icon = R.drawable.ic_add, text = "添加") {
            val name = playerName.trim().replace("\n", "")
            if (PlayerValidator.isValidPlayerName(name)) {
                viewModel.insert(name)
                XToast.showText(context, R.string.added)
                finish()
            } else {
                XToast.showText("玩家名格式错误")
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}