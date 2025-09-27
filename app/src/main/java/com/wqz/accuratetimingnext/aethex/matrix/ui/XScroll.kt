package com.wqz.accuratetimingnext.aethex.matrix.ui

import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XDeviceType
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XSpacing
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 滚动栏
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.9.7
 */
object XScroll {
    /**
     * 纵向滚动栏
     *
     * Created by Wu Qizhen on 2025.9.7
     */
    @Composable
    fun Vertical(
        horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
        verticalArrangement: Arrangement.Vertical = Arrangement.Center,
        horizontalPadding: Dp = 20.dp,
        deviceType: XDeviceType = XDeviceType.PHONE,
        expandToStatusBar: Boolean = false,
        content: @Composable () -> Unit
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    top = if (expandToStatusBar) 0.dp else XSpacing.TOP,
                    bottom = XSpacing.getBottomSpacing(deviceType)
                ),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement
        ) {
            content()
        }
    }

    /**
     * 横向滚动栏
     *
     * Created by Wu Qizhen on 2025.9.7
     */
    @Composable
    fun Horizontal(
        verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
        verticalPadding: Dp = 20.dp,
        deviceType: XDeviceType = XDeviceType.PHONE,
        expandToStatusBar: Boolean = false,
        content: @Composable () -> Unit
    ) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(scrollState)
                .padding(
                    start = verticalPadding,
                    end = verticalPadding,
                    top = if (expandToStatusBar) 0.dp else XSpacing.TOP,
                    bottom = XSpacing.getBottomSpacing(deviceType)
                ),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement
        ) {
            content()
        }
    }
}