package com.wqz.accuratetimingnext.ui.color

import androidx.compose.foundation.text.selection.TextSelectionColors
import com.wqz.accuratetimingnext.ui.theme.ThemeColor

/**
 * 输入框指针颜色
 * Created by Wu Qizhen on 2025.6.12
 */
object SelectionColor {
    val DEFAULT_YELLOW = TextSelectionColors(
        handleColor = ThemeColor,
        backgroundColor = BackgroundColor.PSEUDO_TRANSPARENT_THEME_RED
    )
}