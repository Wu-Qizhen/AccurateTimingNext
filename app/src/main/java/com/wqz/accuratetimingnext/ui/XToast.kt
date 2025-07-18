package com.wqz.accuratetimingnext.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.ui.ModifierExtends.clickVfx
import com.wqz.accuratetimingnext.ui.XBackground.titleBackgroundHorizontalPadding
import com.wqz.accuratetimingnext.ui.color.BackgroundColor
import com.wqz.accuratetimingnext.ui.color.BorderColor

/**
 * 提示栏
 * Created by Wu Qizhen on 2024.7.11
 * Updated by Wu Qizhen on 2025.6.16
 */
object XToast {
    var toastContent by mutableStateOf("")
    var snackBarObject: SnackBarObject? by mutableStateOf(null)

    fun showText(content: String) {
        toastContent = content
    }

    fun showText(context: Context, @StringRes contentId: Int) {
        context.let {
            toastContent = it.getString(contentId)
        }
    }

    fun showSnackBar(
        content: String,
        leadingIcon: ImageVector,
        trailingIcon: ImageVector,
        onClick: () -> Unit
    ) {
        snackBarObject = SnackBarObject(
            text = content,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            onClick = onClick
        )
    }

    @Composable
    fun ToastContent(content: String) {
        Box(
            modifier = Modifier
                .padding(titleBackgroundHorizontalPadding() - 5.dp)
                .fillMaxWidth()
                .border(
                    width = 0.3f.dp,
                    shape = RoundedCornerShape(10.dp),
                    brush = Brush.linearGradient(
                        BorderColor.DEFAULT_GRADIENT_GRAY,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .background(
                    brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0f to Color.Black,
                            0.5f to Color.Black,
                            1f to BackgroundColor.PSEUDO_TRANSPARENT_THEME_RED
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = content,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                fontWeight = FontWeight.Medium
            )
        }
    }

    @Composable
    fun SnackBarContent(
        snackBarObject: SnackBarObject
    ) {
        val localDensity = LocalDensity.current

        Row(
            modifier = Modifier
                .clickVfx {
                    snackBarObject.onClick()
                    XToast.snackBarObject = null
                }
                .padding(titleBackgroundHorizontalPadding() - 5.dp)
                .fillMaxWidth()
                .border(
                    width = 0.3f.dp,
                    shape = RoundedCornerShape(10.dp),
                    brush = Brush.linearGradient(
                        BorderColor.DEFAULT_GRADIENT_GRAY,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .background(
                    brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0f to Color.Black,
                            .5f to Color.Black,
                            1f to BackgroundColor.PSEUDO_TRANSPARENT_THEME_RED
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = snackBarObject.leadingIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(
                    with(localDensity) { 15.sp.toDp() }
                )
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = snackBarObject.text,
                // fontFamily = FontFamily,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium
            )

            Icon(imageVector = snackBarObject.trailingIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(
                    with(localDensity) { 12.sp.toDp() }
                )
            )
        }
    }

    data class SnackBarObject(
        val text: String,
        val leadingIcon: ImageVector,
        val trailingIcon: ImageVector,
        val onClick: () -> Unit
    )
}

@Preview
@Composable
fun ToastContentPreview() {
    XToast.ToastContent(content = "测试")
}