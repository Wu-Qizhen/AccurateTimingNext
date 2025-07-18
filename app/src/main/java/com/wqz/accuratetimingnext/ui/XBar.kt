package com.wqz.accuratetimingnext.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.R
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.theme.AccurateTimingTheme

/**
 * 栏条
 * Created by Wu Qizhen on 2025.7.17
 */
object XBar {
    /**
     * 分类栏
     * @param iconId 图标资源
     * @param textId 文本资源
     */
    @Composable
    fun ClassificationBar(iconId: Int, textId: Int) {
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
                fontFamily = FontFamily(Font(R.font.fzruisuti_regular)),
                fontWeight = FontWeight.Thin
            )
        }
    }

    /**
     * 分类栏
     * @param iconId 图标资源
     * @param text 文本
     */
    @Composable
    fun ClassificationBar(iconId: Int, text: String) {
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
                fontFamily = FontFamily(Font(R.font.fzruisuti_regular)),
                fontWeight = FontWeight.Thin
            )
        }
    }
}

@Preview
@Composable
fun XBarPreview() {
    AccurateTimingTheme {
        XBar.ClassificationBar(
            iconId = R.drawable.ic_grade_easy,
            text = "分类栏"
        )
    }
}