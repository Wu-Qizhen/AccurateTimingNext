package com.wqz.accuratetimingnext.ui

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.theme.AccurateTimingTheme
import com.wqz.accuratetimingnext.ui.theme.ThemeColor

/**
 * 标题栏
 * Created by Wu Qizhen on 2024.6.23
 * Refactored by Wu Qizhen on 2024.11.30
 */
object XTitleBar {
    /**
     * 文本标题栏
     * @param title 标题
     */
    @Composable
    fun TextTitleBar(
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
                    (context as? androidx.activity.ComponentActivity)?.finish()
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
                    tint = ThemeColor
                )
            }

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = FontFamily(
                    Font(R.font.fzruisuti_regular, FontWeight.Normal)
                ),
                maxLines = 1,
                color = ThemeColor,
                textAlign = TextAlign.Center
            )
        }
    }

    /**
     * 文本标题栏
     * @param title 标题
     */
    @Composable
    fun TextTitleBar(title: Int) {
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
                    (context as? androidx.activity.ComponentActivity)?.finish()
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
                    tint = ThemeColor
                )
            }

            Text(
                text = stringResource(id = title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = FontFamily(
                    Font(R.font.fzruisuti_regular, FontWeight.Normal)
                ),
                maxLines = 1,
                color = ThemeColor,
                textAlign = TextAlign.Center
            )
        }
    }

    /**
     * Logo 标题栏
     */
    @Composable
    fun LogoTitleBar() {
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
                    painter = painterResource(id = R.drawable.logo_at_watch),
                    contentDescription = "Logo",
                    modifier = Modifier.size(25.dp),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_at_text),
                    contentDescription = "Text",
                    modifier = Modifier
                        .width(100.dp)
                        .height(23.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Preview
@Composable
fun XTitleBarPreview() {
    AccurateTimingTheme {
        Column {
            XTitleBar.TextTitleBar(R.string.app_name)

            Spacer(modifier = Modifier.height(10.dp))

            XTitleBar.LogoTitleBar()
        }
    }
}