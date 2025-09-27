package com.wqz.accuratetimingnext.aethex.matrix.foundation.property

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * 状态
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.7.11
 * Updated by Wu Qizhen on 2025.7.23
 */
object XStats {
    /**
     * ▲ 判断是否为圆屏
     *
     * Created by Wu Qizhen on 2024.7.11
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun isScreenRound() =
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)*/
        LocalConfiguration.current.isScreenRound /*else false*/
}