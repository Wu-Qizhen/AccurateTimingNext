package com.wqz.accuratetimingnext.aethex.matrix.foundation.property

import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth.BORDER_M
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth.NONE
import androidx.compose.ui.unit.dp

/**
 * 粗细
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.11.24
 */
object XWidth {
    /**
     * 边框粗细
     *
     * @param NONE 边框粗细
     *
     * Updated by Wu Qizhen on 2025.7.21
     */
    val NONE = 0.dp

    /**
     * 边框粗细
     *
     * @param BORDER_M 边框粗细
     * @param BORDER_S 边框粗细
     * @param BORDER_L 边框粗细
     *
     * Updated by Wu Qizhen on 2025.7.21
     */
    val BORDER_M = 0.5f.dp
    val BORDER_S = 0.1f.dp
    val BORDER_L = 1f.dp
}