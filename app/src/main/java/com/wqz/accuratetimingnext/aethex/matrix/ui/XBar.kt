package com.wqz.accuratetimingnext.aethex.matrix.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.typography.XFont

/**
 * 栏条
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.6.23
 * Refactored by Wu Qizhen on 2024.11.30
 * Updated by Wu Qizhen on 2025.7.23
 */
object XBar {
    /**
     * ▼ 文本标题栏
     *
     * @param title 标题
     *
     * Created by Wu Qizhen on 2024.6.23
     * Refactored by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Text(
        title: String = "主页"
    ) {
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickVfx(
                    interactionSource = interactionSource
                ) {
                    (context as? ComponentActivity)?.finish()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isPressed.value,
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(18.dp),
                    tint = XThemeColor.NORMAL
                )
            }

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = XFont.THEME,
                maxLines = 1,
                color = XThemeColor.NORMAL,
                textAlign = TextAlign.Center
            )
        }
    }

    /**
     * ▼ 文本标题栏
     *
     * @param title 标题
     *
     * Created by Wu Qizhen on 2024.6.23
     * Refactored by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Text(title: Int) {
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickVfx(
                    interactionSource = interactionSource
                ) {
                    (context as? ComponentActivity)?.finish()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isPressed.value,
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(18.dp),
                    tint = XThemeColor.NORMAL
                )
            }

            Text(
                text = stringResource(id = title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = XFont.THEME,
                maxLines = 1,
                color = XThemeColor.NORMAL,
                textAlign = TextAlign.Center
            )
        }
    }

    /**
     * 徽标标题栏
     *
     * Created by Wu Qizhen on 2024.6.23
     * Refactored by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Logo(
        logo: Int,
        title: Int
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 10.dp)
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(25.dp),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(id = title),
                    contentDescription = "Text",
                    modifier = Modifier
                        .width(100.dp)
                        .height(23.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    /**
     * 徽标标题栏
     *
     * Created by Wu Qizhen on 2025.9.6
     */
    @Composable
    fun Logo(
        logo: Int,
        title: String
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 10.dp)
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(25.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = XFont.THEME,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    /**
     * ▼ 分类栏
     *
     * @param iconId 图标资源
     * @param textId 文本资源
     *
     * Created by Wu Qizhen on 2025.7.17
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Classification(iconId: Int, textId: Int) {
        Row(
            modifier = Modifier
                .clickVfx()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = stringResource(id = textId),
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = textId),
                fontSize = 16.sp,
                fontFamily = XFont.THEME,
                fontWeight = FontWeight.Thin
            )
        }
    }

    /**
     * ▼ 分类栏
     *
     * @param iconId 图标资源
     * @param text 文本
     *
     * Created by Wu Qizhen on 2025.7.17
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Classification(iconId: Int, text: String) {
        Row(
            modifier = Modifier
                .clickVfx()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = text,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = XFont.THEME,
                fontWeight = FontWeight.Thin
            )
        }
    }
}

/*
@preview
@Composable
fun XBarPreview() {
    XTheme {
        Column {
            XBar.Text(R.string.app_name)

            Spacer(modifier = Modifier.height(10.dp))

            XBar.Logo()
        }
    }
}*/

/*
@preview
@Composable
fun XBarPreview() {
    XTheme {
        XBar.Classification(
            iconId = R.drawable.logo_aethex_matrix,
            text = "分类栏"
        )
    }
}*/