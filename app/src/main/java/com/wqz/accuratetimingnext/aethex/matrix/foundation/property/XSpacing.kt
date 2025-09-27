package com.wqz.accuratetimingnext.aethex.matrix.foundation.property

import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.BOTTOM_P
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.BOTTOM_W
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.COMPONENT_L
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.COMPONENT_M
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.COMPONENT_S
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing.COMPONENT_SS
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 间距系统
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.7.21
 */
object XSpacing {
    /*val NONE: Dp = 0.dp*/

    /*val XXXS: Dp = 1.dp
    val XXS: Dp = 2.dp
    val XS: Dp = 4.dp
    val SM: Dp = 8.dp
    val MD: Dp = 16.dp
    val LG: Dp = 24.dp
    val XL: Dp = 32.dp
    val XXL: Dp = 64.dp
    val XXXL: Dp = 128.dp*/

    /**
     * 组件间距
     * 用于组件内边距、组件外间距、组件之间间距
     *
     * @param COMPONENT_SS 0.5X 间距
     * @param COMPONENT_S 默认间距
     * @param COMPONENT_M 1.5X 间距
     * @param COMPONENT_L 2X 间距
     *
     * Updated by Wu Qizhen on 2025.7.21
     */
    val COMPONENT_SS: Dp = 5.dp
    val COMPONENT_S: Dp = 10.dp
    val COMPONENT_M: Dp = 15.dp
    val COMPONENT_L: Dp = 20.dp

    /**
     * ▼ 顶部间距
     * 用于状态栏顶部间距
     *
     * Updated by Wu Qizhen on 2025.7.21
     */
    val TOP: Dp = 50.dp

    /**
     * 底部间距
     * 用于界面底部间距
     *
     * @param BOTTOM_P 移动设备
     * @param BOTTOM_W 穿戴设备
     *
     * Updated by Wu Qizhen on 2025.7.21
     */
    val BOTTOM_P: Dp = 20.dp
    val BOTTOM_W: Dp = 50.dp

    /**
     * 根据设备类型返回对应的底部间距
     *
     * @param deviceType 设备类型
     * @return 对应的底部间距值
     *
     * Created by Wu Qizhen on 2025.9.7
     */
    fun getBottomSpacing(deviceType: XDeviceType): Dp {
        return when (deviceType) {
            XDeviceType.PHONE -> BOTTOM_P
            XDeviceType.WEARABLE -> BOTTOM_W
            else -> BOTTOM_P
        }
    }
}