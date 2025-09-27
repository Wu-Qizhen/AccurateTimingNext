package com.wqz.accuratetimingnext.aethex.matrix.foundation.theme

import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBackgroundColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XContentColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XTextFieldColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.typography.XType
import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * ▼ 浅色系统
 *
 * Created by Wu Qizhen on 2024.7.2
 * Updated by Wu Qizhen on 2025.7.23
 */
val LightColorScheme = lightColorScheme(
    primary = XThemeColor.DEEP,
    onPrimary = XThemeColor.LIGHT,
    secondary = Color.White,
    onSecondary = Color.Black,
    tertiary = Color.White,
    onTertiary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    inverseSurface = Color.Gray,
    inverseOnSurface = Color.White,
    error = XContentColor.RED,
    onError = XBackgroundColor.RED
)

/**
 * ▼ 深色系统
 *
 * Created by Wu Qizhen on 2024.7.2
 * Updated by Wu Qizhen on 2025.7.23
 */
val DarkColorScheme = darkColorScheme(
    primary = XThemeColor.DEEP,
    onPrimary = XThemeColor.NORMAL,
    secondary = Color.Black,
    onSecondary = Color.White,
    tertiary = Color.Black,
    onTertiary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    inverseSurface = Color.White,
    inverseOnSurface = Color.Gray,
    error = XContentColor.RED,
    onError = XBackgroundColor.RED
)

/**
 * 主题
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.7.2
 * Updated by Wu Qizhen on 2025.7.23
 */
@Composable
fun XTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current

    if (darkTheme && !view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // 关键修改：强制设置为黑色
            window.statusBarColor = Color.Black.toArgb()
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            // 确保状态栏图标可见（亮色图标）
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = XType.TYPOGRAPHY
    ) {
        // 在 MaterialTheme 内部覆盖 CompositionLocal
        CompositionLocalProvider(
            LocalTextSelectionColors provides XTextFieldColor.TEXT_FIELD_SELECTION
        ) {
            // 应用您的内容
            content()
        }
    }
}