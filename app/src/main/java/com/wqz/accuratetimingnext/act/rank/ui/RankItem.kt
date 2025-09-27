package com.wqz.accuratetimingnext.act.rank.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor

/**
 * 排行榜列表项
 * Created by Wu Qizhen on 2025.7.17
 */
@Composable
fun RankItem(
    rank: Int,
    playerId: Int,
    playerName: String,
    data: Int
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val contentColor =
        if (isPressed.value) Color.Gray else Color.White

    /*Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp)
    ) {*/
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 15.dp, 10.dp)
            .clickVfx(interactionSource)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(alignment = Alignment.CenterStart),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (rank) {
                1 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_gold_medal),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                2 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_silver_medal),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                3 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bronze_medal),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                4 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_four),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                5 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_five),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                6 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_six),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                7 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_seven),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                8 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_eight),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                9 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_nine),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                10 -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ten),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }

                else -> {
                    Box(modifier = Modifier.size(35.dp)) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "$rank",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = XThemeColor.NORMAL
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = playerName,
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
                        text = "$playerId",
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }

        Text(
            text = if (data == -1) "--" else data.toString(),
            modifier = Modifier
                // .size(35.dp)
                .align(Alignment.CenterEnd),
            fontSize = when (data.toString().length) {
                1 -> 25.sp
                2 -> 25.sp
                3 -> 20.sp
                4 -> 16.sp
                5 -> 12.sp
                else -> 10.sp
            },
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = contentColor
        )
    }
}