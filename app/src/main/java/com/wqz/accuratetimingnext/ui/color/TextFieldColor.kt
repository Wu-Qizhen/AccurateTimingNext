package com.wqz.accuratetimingnext.ui.color

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.wqz.accuratetimingnext.ui.theme.ThemeColor

/**
 * 输入框主题颜色
 * Created by Wu Qizhen on 2025.6.12
 */
object TextFieldColor {
    @Composable
    fun colors(): TextFieldColors {
        return TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, // 背景颜色
            focusedContainerColor = Color.Transparent, // 背景颜色
            unfocusedIndicatorColor = Color.Transparent, // 下划线颜色
            focusedIndicatorColor = Color.Transparent, // 下划线颜色
            focusedLabelColor = Color.White, // 标签颜色
            unfocusedLabelColor = Color.Gray, // 标签颜色
            cursorColor = ThemeColor, // 光标颜色
        )
    }
}