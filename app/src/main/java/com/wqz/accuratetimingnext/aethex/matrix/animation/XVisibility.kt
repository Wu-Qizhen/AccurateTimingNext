package com.wqz.accuratetimingnext.aethex.matrix.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 动画可见性
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.7.11
 * Updated by Wu Qizhen on 2025.7.22
 */
object XVisibility {
    /**
     * 缩放淡入淡出
     *
     * Created by Wu Qizhen on 2024.7.11
     */
    @Composable
    fun ScaleFade(
        visible: Boolean,
        modifier: Modifier = Modifier,
        enter: EnterTransition = fadeIn() + expandIn(),
        exit: ExitTransition = shrinkOut() + fadeOut(),
        label: String = "XVisibility",
        content: @Composable () -> Unit
    ) {
        AnimatedVisibility(visible, modifier, enter, exit, label) {
            content()
        }
    }
}
