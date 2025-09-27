package com.wqz.accuratetimingnext.aethex.matrix.ui

import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBackgroundColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBorderColor
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

/**
 * 卡片
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.11.29
 * Updated by Wu Qizhen on 2025.7.23
 */
object XCard {
    /**
     * 平面卡片（无按压效果）
     *
     * Created by Wu Qizhen on 2024.11.29
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Surface(
        padding: Int = 0,
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = XBackgroundColor.GRAY,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = XWidth.BORDER_M,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        XBorderColor.GRAY_GRADIENT,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(padding.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

    /**
     * 灵动卡片（有按压效果）
     *
     * Created by Wu Qizhen on 2024.11.29
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Lively(
        padding: Int = 0,
        content: @Composable () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) XBackgroundColor.GRAY_PRESSED else XBackgroundColor.GRAY

        Column(
            modifier = Modifier
                .clickVfx(
                    interactionSource = interactionSource,
                    enabled = true
                )
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = XWidth.BORDER_M,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        XBorderColor.GRAY_GRADIENT,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(padding.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

    /**
     * 灵动卡片（有按压效果）
     *
     * Created by Wu Qizhen on 2025.9.6
     */
    @Composable
    fun Lively(
        padding: Int = 0,
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) XBackgroundColor.GRAY_PRESSED else XBackgroundColor.GRAY

        Column(
            modifier = modifier
                .clickVfx(
                    interactionSource = interactionSource,
                    enabled = true
                )
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = XWidth.BORDER_M,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        XBorderColor.GRAY_GRADIENT,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(padding.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

/*
@preview
@Composable
fun XCardPreview() {
    XTheme {
        XCard.SurfaceCard {}
    }
}*/