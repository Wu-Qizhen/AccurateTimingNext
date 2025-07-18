package com.wqz.accuratetimingnext.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.AccurateTimingTheme

/**
 * 分割线
 * Created by Wu Qizhen on 2025.7.17
 */
object XDivider {
    /**
     * 默认分割线
     */
    @Composable
    fun Horizontal() {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = BorderWidth.DEFAULT_WIDTH,
            color = BorderColor.DEFAULT_GRAY
        )
    }
}

@Preview
@Composable
fun XDividerPreview() {
    AccurateTimingTheme {
        XDivider.Horizontal()
    }
}