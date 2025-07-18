package com.wqz.accuratetimingnext.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.color.BackgroundColor
import com.wqz.accuratetimingnext.ui.color.BorderColor
import com.wqz.accuratetimingnext.ui.property.BorderWidth
import com.wqz.accuratetimingnext.ui.theme.AccurateTimingTheme

/**
 * 卡片
 * Created by Wu Qizhen on 2024.11.29
 */
object XCard {
    /**
     * 平面卡片（无按压效果）
     */
    @Composable
    fun SurfaceCard(
        padding: Int = 0,
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = BackgroundColor.DEFAULT_GRAY,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = BorderWidth.DEFAULT_WIDTH,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        BorderColor.DEFAULT_GRADIENT_GRAY,
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
     */
    @Composable
    fun LivelyCard(
        padding: Int = 0,
        content: @Composable () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BackgroundColor.PRESSED_GRAY else BackgroundColor.DEFAULT_GRAY

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
                    width = BorderWidth.DEFAULT_WIDTH,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        BorderColor.DEFAULT_GRADIENT_GRAY,
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

@Preview
@Composable
fun XCardPreview() {
    AccurateTimingTheme {
        XCard.SurfaceCard {}
    }
}