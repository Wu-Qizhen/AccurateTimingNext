package com.wqz.accuratetimingnext.ui.theme

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
import com.wqz.accuratetimingnext.ui.color.SelectionColor

/**
 * 主题
 * Created by Wu Qizhen on 2025.7.3
 */
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    onPrimary = Color.Black,
    secondary = Color.White,
    onSecondary = Color.Black,
    tertiary = Color.Gray,
    onTertiary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    onPrimary = Color.Black,
    secondary = Color.Black,
    onSecondary = Color.White,
    tertiary = Color.LightGray,
    onTertiary = Color.Black
)

@Composable
fun AccurateTimingTheme(
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

    if (!view.isInEditMode) {
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

    // 使用 CompositionLocalProvider 覆盖全局设置
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        // 在 MaterialTheme 内部覆盖 CompositionLocal
        CompositionLocalProvider(
            LocalTextSelectionColors provides SelectionColor.DEFAULT_YELLOW
        ) {
            // 应用您的内容
            content()
        }
    }
}