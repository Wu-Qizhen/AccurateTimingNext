package com.wqz.accuratetimingnext.aethex.matrix.foundation.color

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

/**
 * 颜色
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.7.26
 */
sealed class XColor {
    /**
     * 无颜色
     *
     * Created by Wu Qizhen on 2025.7.26
     */
    object None : XColor()

    /**
     * 单色
     *
     * Created by Wu Qizhen on 2025.7.26
     */
    data class Single(val color: Color) : XColor()

    /**
     * 渐变色
     *
     * Created by Wu Qizhen on 2025.7.26
     */
    /*data class Gradient(val colors: List<Color>) : XColor()*/
    // 线性渐变（水平 / 垂直 / 对角）
    data class LinearGradient(
        val colors: List<Color>,
        val start: Alignment = Alignment.TopStart,
        val end: Alignment = Alignment.BottomEnd,
        val tileMode: TileMode = TileMode.Clamp
    ) : XColor()

    // 径向渐变
    data class RadialGradient(
        val colors: List<Color>,
        val center: Alignment = Alignment.Center,
        val radius: Float = 0.5f,
        val tileMode: TileMode = TileMode.Clamp
    ) : XColor()

    // 扫描渐变（旋转效果）
    data class SweepGradient(
        val colors: List<Color>,
        val center: Alignment = Alignment.Center,
        val startAngle: Float = 0f,
        val endAngle: Float = 360f
    ) : XColor()
}