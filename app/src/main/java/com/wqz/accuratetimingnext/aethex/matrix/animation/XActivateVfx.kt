package com.wqz.accuratetimingnext.aethex.matrix.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

/**
 * 修饰符扩展
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.6.16
 * Updated by Wu Qizhen on 2025.7.22
 */
object XActivateVfx {
    /**
     * 按压缩放效果
     * 该函数用于响应点击手势，并在元素被按下时应用缩放动画
     *
     * @param interactionSource 交互源，默认为 rememberMutableInteractionSource()
     * @param enabled 是否启用点击反馈效果，默认为 true
     * @param onClick 点击事件
     * @return 返回一个应用了点击反馈效果的 Modifier
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun Modifier.clickVfx(
        interactionSource: MutableInteractionSource = rememberMutableInteractionSource(),
        enabled: Boolean = true,
        onClick: () -> Unit,
    ): Modifier = composed {
        if (enabled) {
            val isPressed by interactionSource.collectIsPressedAsState()
            val sizePercent by animateFloatAsState(
                targetValue = if (isPressed) 0.95f else 1f,
                animationSpec = tween(durationMillis = 100), label = ""
            )
            scale(sizePercent).clickable(
                indication = null, interactionSource = interactionSource, onClick = onClick
            )
        } else {
            Modifier.Companion
        }
    }

    /**
     * 按压缩放效果
     * 该函数用于响应点击、长按和双击手势，并在元素被按下时应用缩放动画
     * 如果交互源发生变化或点击事件发生变化，会重新计算修饰符
     *
     * @param interactionSource 用于管理交互状态的可变交互源，默认为 rememberMutableInteractionSource()
     * @param enabled 是否启用点击反馈效果，默认为 true
     * @param onClick 点击事件的回调，默认为空操作
     * @param onLongClick 长按事件的回调，默认为空操作
     * @return 返回一个应用了点击反馈效果的 Modifier
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun Modifier.clickVfx(
        interactionSource: MutableInteractionSource = rememberMutableInteractionSource(),
        enabled: Boolean = true,
        onClick: () -> Unit = {},
        onLongClick: () -> Unit = {}
    ): Modifier = composed {
        if (enabled) {
            val isPressed by interactionSource.collectIsPressedAsState()
            val sizePercent by animateFloatAsState(
                targetValue = if (isPressed) 0.95f else 1f,
                animationSpec = tween(durationMillis = 100), label = ""
            )
            scale(sizePercent).pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = {
                        onLongClick()
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    })
            }
        } else {
            Modifier.Companion
        }
    }

    /**
     * 按压缩放效果
     * 该函数用于响应点击、长按和双击手势，并在元素被按下时应用缩放动画
     * 如果交互源发生变化或点击事件发生变化，会重新计算修饰符
     *
     * @param interactionSource 用于管理交互状态的可变交互源，默认为 rememberMutableInteractionSource()
     * @param enabled 是否启用点击反馈效果，默认为 true
     * @param onClick 点击事件的回调，默认为空操作
     * @param onLongClick 长按事件的回调，默认为空操作
     * @param onDoubleClick 双击事件的回调，默认为空操作
     * @return 返回一个应用了点击反馈效果的 Modifier
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun Modifier.clickVfx(
        interactionSource: MutableInteractionSource = rememberMutableInteractionSource(),
        enabled: Boolean = true,
        onClick: () -> Unit = {},
        onLongClick: () -> Unit = {},
        onDoubleClick: () -> Unit = {}
    ): Modifier = composed {
        // 当点击反馈效果启用时
        if (enabled) {
            // 收集交互源的按下状态
            val isPressed by interactionSource.collectIsPressedAsState()
            // 动态调整大小百分比，按下时缩小到 95%，非按下时恢复原大小，动画持续 100 毫秒
            val sizePercent by animateFloatAsState(
                targetValue = if (isPressed) 0.95f else 1f,
                animationSpec = tween(durationMillis = 100), label = ""
            )
            // 应用缩放效果，并监听点击、长按和双击手势
            scale(sizePercent).pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = {
                        onLongClick()
                    },
                    onDoubleTap = {
                        onDoubleClick()
                    },
                )
            }
        } else {
            // 当点击反馈效果未启用时，直接返回默认修饰符
            Modifier.Companion
        }
    }

    /**
     * 创建一个可变交互源
     * 该函数用于创建一个可变交互源，该交互源用于管理元素的交互状态，如按下、释放、点击等
     *
     * @return 返回一个可变交互源
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2025.7.22
     */
    @Composable
    fun rememberMutableInteractionSource() = remember {
        MutableInteractionSource()
    }
}