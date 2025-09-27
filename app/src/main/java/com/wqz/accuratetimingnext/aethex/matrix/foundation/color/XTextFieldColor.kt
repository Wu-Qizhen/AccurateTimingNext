package com.wqz.accuratetimingnext.aethex.matrix.foundation.color

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * 输入框颜色
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.6.12
 * Updated by Wu Qizhen on 2025.7.22
 */
object XTextFieldColor {
    /**
     * ▼ 输入框界面颜色
     *
     * Created by Wu Qizhen on 2025.6.12
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun textFieldSurface(): TextFieldColors {
        return TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, // 默认背景颜色
            focusedContainerColor = Color.Transparent, // 聚焦背景颜色
            unfocusedIndicatorColor = Color.Transparent, // 默认下划线颜色
            focusedIndicatorColor = Color.Transparent, // 聚焦下划线颜色
            unfocusedLabelColor = Color.Gray, // 默认标签颜色
            focusedLabelColor = Color.White, // 聚焦标签颜色
            cursorColor = XThemeColor.NORMAL, // 光标颜色
        )
    }
    /*val TEXT_FIELD_SURFACE = TextFieldDefaults.colors(
           unfocusedContainerColor = Color.Transparent, // 默认背景颜色
           focusedContainerColor = Color.Transparent, // 聚焦背景颜色
           unfocusedIndicatorColor = Color.Transparent, // 默认下划线颜色
           focusedIndicatorColor = Color.Transparent, // 聚焦下划线颜色
           unfocusedLabelColor = Color.Gray, // 默认标签颜色
           focusedLabelColor = Color.White, // 聚焦标签颜色
           cursorColor = XThemeColor.NORMAL, // 光标颜色
       )*/

    /**
     * ▼ 输入框指针颜色
     *
     * Created by Wu Qizhen on 2025.6.12
     * Updated by Wu Qizhen on 2025.7.22
     */
    val TEXT_FIELD_SELECTION = TextSelectionColors(
        handleColor = XThemeColor.NORMAL,
        backgroundColor = XThemeColor.DARK
    )
    /*fun textFieldSelection(): TextSelectionColors {
        return TextSelectionColors(
            handleColor = THEME_COLOR,
            backgroundColor = THEME_COLORS.dark
        )
    }*/
}