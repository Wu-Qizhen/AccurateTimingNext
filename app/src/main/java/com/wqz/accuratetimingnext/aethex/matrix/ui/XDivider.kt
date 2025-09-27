package com.wqz.accuratetimingnext.aethex.matrix.ui

import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBorderColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * 分割线
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2025.7.17
 * Updated by Wu Qizhen on 2025.7.22
 */
object XDivider {
    /**
     * 默认分割线
     *
     * Created by Wu Qizhen on 2025.7.17
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun Horizontal() {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = XWidth.BORDER_M,
            color = XBorderColor.GRAY
        )
    }

    @Composable
    fun Horizontal(
        space: Dp
    ) {
        Spacer(modifier = Modifier.height(space))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = XWidth.BORDER_M,
            color = XBorderColor.GRAY
        )

        Spacer(modifier = Modifier.height(space))
    }
}

/*
@preview
@Composable
fun XDividerPreview() {
    XTheme {
        XDivider.Horizontal()
    }
}*/