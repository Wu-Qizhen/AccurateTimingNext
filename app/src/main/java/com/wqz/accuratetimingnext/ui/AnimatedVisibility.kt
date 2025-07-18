package com.wqz.accuratetimingnext.ui

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
 * Created by Wu Qizhen on 2024.7.11
 */
@Composable
fun AnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    label: String = "AnimatedVisibility",
    content: @Composable /*AnimatedVisibilityScope.*/() -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(visible, modifier, enter, exit, label) {
        content()
    }
}