package com.wqz.accuratetimingnext.act.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.about.AboutAppActivity
import com.wqz.accuratetimingnext.act.player.PlayerListActivity
import com.wqz.accuratetimingnext.act.time.TimeListActivity
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem
import com.wqz.accuratetimingnext.aethex.matrix.ui.XToast
import com.wqz.accuratetimingnext.preference.LayoutPreferencesManager

/**
 * 设置
 * Created by Wu Qizhen on 2024.6.29
 */
class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(titleId = R.string.settings) {
                SettingScreen()
            }
        }
    }

    @Composable
    fun SettingScreen() {
        val context = LocalContext.current
        val layoutPrefs = remember { LayoutPreferencesManager(context = context) }
        val isGirdLayout = remember { mutableStateOf(layoutPrefs.isGridLayout()) }

        XItem.Capsule(
            icon = R.drawable.ic_player,
            text = "玩家管理"
        ) {
            context.startActivity(Intent(context, PlayerListActivity::class.java))
        }

        Spacer(modifier = Modifier.height(5.dp))

        XItem.Capsule(
            icon = R.drawable.ic_time,
            text = "时间管理"
        ) {
            context.startActivity(Intent(context, TimeListActivity::class.java))
        }

        Spacer(modifier = Modifier.height(5.dp))

        /*XItem.Capsule(
            icon = R.drawable.ic_backup,
            text = stringResource(R.string.backup)
        ) {
            val intent = Intent(context, PasswordCheckActivity::class.java)
            intent.putExtra("REQUEST_CODE", 2)
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(5.dp))

        XItem.Capsule(
            icon = R.drawable.ic_recovery,
            text = stringResource(R.string.recovery)
        ) {
            val intent = Intent(context, PasswordCheckActivity::class.java)
            intent.putExtra("REQUEST_CODE", 3)
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(5.dp))*/

        XItem.Switch(
            icon = R.drawable.ic_layout,
            text = stringResource(R.string.gird_layout),
            subText = stringResource(R.string.homepage_gird_layout),
            status = isGirdLayout
        ) {
            isGirdLayout.value = !isGirdLayout.value
            layoutPrefs.setLayoutType(if (isGirdLayout.value) LayoutPreferencesManager.LAYOUT_TYPE_GRID else LayoutPreferencesManager.LAYOUT_TYPE_LIST)
            XToast.showText("设置将在重启应用后生效")
        }

        Spacer(modifier = Modifier.height(5.dp))

        XItem.Capsule(
            icon = R.drawable.ic_about,
            text = stringResource(R.string.about)
        ) {
            context.startActivity(Intent(context, AboutAppActivity::class.java))
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}