package com.wqz.accuratetimingnext.aethex.matrix.foundation.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.wqz.accuratetimingnext.R

/**
 * 字体
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.7.22
 */
object XFont {
    /**
     * ▲ 字体
     *
     * Created by Wu Qizhen on 2025.7.22
     */
    val THEME = FontFamily(Font(R.font.fzruisuti_regular, FontWeight.Normal))
    val GENERAL = FontFamily(Font(R.font.misans_regular, FontWeight.Normal))
}