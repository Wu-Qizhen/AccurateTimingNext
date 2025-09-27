package com.wqz.accuratetimingnext.aethex.matrix.foundation.color

import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor.THEME_BLUE
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor.THEME_GREEN
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor.THEME_ORANGE
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor.THEME_RED
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor.THEME_YELLOW
import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 主题颜色
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.7.22
 */
object XThemeColor {
    /**
     * 主题颜色
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    data class ThemeColors(
        val light: Color,
        val normal: Color,
        val deep: Color,
        val dark: Color
    )

    /**
     * 预设主题原色
     *
     * @param THEME_RED 红色
     * @param THEME_ORANGE 橙色
     * @param THEME_YELLOW 黄色
     * @param THEME_GREEN 绿色
     * @param THEME_BLUE 蓝色
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    private val THEME_RED = ThemeColors(
        Color(243, 117, 134, 255),
        Color(239, 0, 39, 255),
        Color(168, 1, 28, 255),
        Color(76, 0, 12, 255)
    )
    private val THEME_ORANGE = ThemeColors(
        Color(255, 158, 72, 255),
        Color(255, 82, 0, 255),
        Color(255, 57, 1, 255),
        Color(235, 74, 24, 255)
    )
    private val THEME_YELLOW = ThemeColors(
        Color(255, 241, 179, 255),
        Color(254, 195, 58, 255),
        Color(199, 160, 37, 255),
        Color(104, 78, 23, 255)
    )
    private val THEME_GREEN = ThemeColors(
        Color(108, 225, 161, 255),
        Color(107, 230, 118, 255),
        Color(94, 194, 113, 255),
        Color(85, 129, 106, 255)
    )
    private val THEME_BLUE = ThemeColors(
        Color(96, 183, 245, 255),
        Color(74, 130, 242, 255),
        Color(51, 91, 237, 255),
        Color(54, 105, 238, 255)
    )

    /**
     * ▲ 主题颜色
     * 使用预设或通过主题颜色生成色阶
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    // val THEME_COLORS = generateThemeColors(Color(255, 255, 255, 255))
    private val THEME_COLORS: ThemeColors = THEME_RED

    /**
     * 通过主题颜色生成色阶
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    private fun generateThemeColors(themeColor: Color): ThemeColors {
        // 将 Compose 的 Color 转换为 HSL 表示
        val hsl = rgbToHsl(themeColor)
        val (h, s, l) = hsl

        // 计算浅色版本（提高亮度，降低饱和度）
        val lightL = min(0.93f, l * 1.4f)
        val lightS = max(0.4f, s * 0.7f)
        val lightColor = hslToRgb(h, lightS, lightL)

        // 计算深色版本（降低亮度，增加饱和度）
        val deepL = max(0.25f, l * 0.65f)
        val deepS = min(0.95f, s * 1.1f)
        val deepColor = hslToRgb(h, deepS, deepL)

        // 计算更深色版本（大幅降低亮度）
        val darkL = max(0.15f, l * 0.35f)
        val darkS = s * 0.85f
        val darkColor = hslToRgb(h, darkS, darkL)

        return ThemeColors(
            light = lightColor,
            normal = themeColor,
            deep = deepColor,
            dark = darkColor
        )
    }

    /**
     * 将 RGB 颜色转换为 HSL 表示
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    private fun rgbToHsl(color: Color): Triple<Float, Float, Float> {
        val r = color.red
        val g = color.green
        val b = color.blue

        val max = maxOf(r, g, b)
        val min = minOf(r, g, b)
        var h: Float
        val l = (max + min) / 2

        if (max == min) {
            h = 0f // 无色相（灰度）
        } else {
            val d = max - min
            h = when (max) {
                r -> ((g - b) / d + (if (g < b) 6 else 0)) * 60
                g -> ((b - r) / d + 2) * 60
                else -> ((r - g) / d + 4) * 60
            }
            h %= 360
            if (h < 0) h += 360
        }

        val s = if (max == 0f || min == 1f) {
            0f
        } else {
            (max - min) / (1 - abs(2 * l - 1))
        }

        return Triple(h, s, l)
    }

    /**
     * 将 HSL 颜色表示转换为 RGB
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    private fun hslToRgb(h: Float, s: Float, l: Float): Color {
        val c = (1 - abs(2 * l - 1)) * s
        val x = c * (1 - abs((h / 60) % 2 - 1))
        val m = l - c / 2

        val (r, g, b) = when {
            h < 60 -> Triple(c, x, 0f)
            h < 120 -> Triple(x, c, 0f)
            h < 180 -> Triple(0f, c, x)
            h < 240 -> Triple(0f, x, c)
            h < 300 -> Triple(x, 0f, c)
            else -> Triple(c, 0f, x)
        }

        return Color(
            red = (r + m).coerceIn(0f, 1f),
            green = (g + m).coerceIn(0f, 1f),
            blue = (b + m).coerceIn(0f, 1f)
        )
    }

    /**
     * 主题颜色
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    val NORMAL = THEME_COLORS.normal
    val LIGHT = THEME_COLORS.light
    val DEEP = THEME_COLORS.deep
    val DARK = THEME_COLORS.dark
    /*fun normal(): Color {
        return THEME_COLORS.normal
    }*/
}