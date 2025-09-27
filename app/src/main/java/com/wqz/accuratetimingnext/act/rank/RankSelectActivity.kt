@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wqz.accuratetimingnext.act.rank

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.act.rank.util.Sort
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBackground
import com.wqz.accuratetimingnext.aethex.matrix.ui.XBar
import com.wqz.accuratetimingnext.aethex.matrix.ui.XItem

/**
 * 排行榜
 * Created by Wu Qizhen on 2025.7.17
 */
class RankSelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XBackground.Breathing(titleId = R.string.ranking_list) {
                RankSelectScreen()
            }
        }
    }

    @Composable
    fun RankSelectScreen() {
        XBar.Classification(iconId = R.drawable.ic_accumulation, textId = R.string.accumulation)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Card(
                icon = R.drawable.ic_number_of_game,
                text = "游戏局数"
            ) {
                val intent = Intent(this@RankSelectActivity, RankListActivity::class.java)
                intent.putExtra("SORT", Sort.NUMBER_OF_GAME.name)
                startActivity(intent)
            }

            Spacer(modifier = Modifier.width(5.dp))

            XItem.Card(
                icon = R.drawable.ic_accumulation_number,
                text = "卡时次数"
            ) {
                val intent = Intent(this@RankSelectActivity, RankListActivity::class.java)
                intent.putExtra("SORT", Sort.ACCUMULATION_NUMBER.name)
                startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Card(
                icon = R.drawable.ic_accumulation_error,
                text = "累计误差"
            ) {
                val intent = Intent(this@RankSelectActivity, RankListActivity::class.java)
                intent.putExtra("SORT", Sort.ACCUMULATION_ERROR.name)
                startActivity(intent)
            }

            Spacer(modifier = Modifier.width(5.dp))

            XItem.Card(
                icon = R.drawable.ic_accumulation_time,
                text = "累计时间"
            ) {
                val intent = Intent(this@RankSelectActivity, RankListActivity::class.java)
                intent.putExtra("SORT", Sort.ACCUMULATION_TIME.name)
                startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        XBar.Classification(iconId = R.drawable.ic_strength, textId = R.string.strength)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Card(
                icon = R.drawable.ic_best,
                text = "最佳记录"
            ) {
                val intent = Intent(this@RankSelectActivity, RankFilterListActivity::class.java)
                intent.putExtra("SORT", Sort.BEST_RECORD.name)
                startActivity(intent)
            }

            Spacer(modifier = Modifier.width(5.dp))

            XItem.Card(
                icon = R.drawable.ic_max,
                text = "最大平均"
            ) {
                val intent = Intent(this@RankSelectActivity, RankFilterListActivity::class.java)
                intent.putExtra("SORT", Sort.MAXIMUM_AVERAGE_ERROR.name)
                startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            XItem.Card(
                icon = R.drawable.ic_min,
                text = "最小平均"
            ) {
                val intent = Intent(this@RankSelectActivity, RankFilterListActivity::class.java)
                intent.putExtra("SORT", Sort.MINIMUM_AVERAGE_ERROR.name)
                startActivity(intent)
            }

            Spacer(modifier = Modifier.width(5.dp))

            Surface(
                modifier = Modifier.size(85.dp),
                color = Color.Transparent
            ) {}
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}