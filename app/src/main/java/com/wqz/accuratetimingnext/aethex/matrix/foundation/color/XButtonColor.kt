package com.wqz.accuratetimingnext.aethex.matrix.foundation.color

import androidx.compose.ui.graphics.Color

/**
 * 类型
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.12.22
 * Updated by Wu Qizhen on 2025.7.22
 */
object XButtonColor {
    /**
     * 警告类型
     *
     * Created by Wu Qizhen on 2024.12.22
     * Updated by Wu Qizhen on 2025.7.22
     */
    val WARNING = ButtonColor(
        background = XBackgroundColor.RED,
        backgroundPressed = XBackgroundColor.RED_PRESSED,
        content = XContentColor.RED
    )

    /**
     * 安全类型
     *
     * Created by Wu Qizhen on 2024.12.22
     * Updated by Wu Qizhen on 2025.7.22
     */
    val SAFE = ButtonColor(
        background = XBackgroundColor.GREEN,
        backgroundPressed = XBackgroundColor.GREEN_PRESSED,
        content = XContentColor.GREEN
    )
    /*val WARNING = listOf(
        XBackgroundColor.RED,
        XBackgroundColor.RED_PRESSED,
        XContentColor.RED
    )
    val SAFE = listOf(
        XBackgroundColor.GREEN,
        XBackgroundColor.GREEN_PRESSED,
        XContentColor.GREEN
    )*/

    data class ButtonColor(
        val background: Color,
        val backgroundPressed: Color,
        val content: Color
    )
}