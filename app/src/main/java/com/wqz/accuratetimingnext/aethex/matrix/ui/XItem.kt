package com.wqz.accuratetimingnext.aethex.matrix.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wqz.accuratetimingnext.aethex.matrix.animation.XActivateVfx.clickVfx
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBackgroundColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XBorderColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XButtonColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.color.XThemeColor
import com.wqz.accuratetimingnext.aethex.matrix.foundation.property.XWidth

/**
 * 胶囊按钮 -> 项目按钮
 *
 * 定位以快速修改配置项
 * ▲：建议修改
 * ▼：可选修改（深度定制）、可增加配置项
 * 无标识符：不可修改
 *
 * Created by Wu Qizhen on 2024.6.16
 * Updated by Wu Qizhen on 2024.8.31
 * Updated by Wu Qizhen on 2024.11.30
 * Updated by Wu Qizhen on 2024.12.31
 * Updated by Wu Qizhen on 2025.7.23
 */
object XItem {
    /**
     * 颜色配置
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    private val BACKGROUND_GRAY = XBackgroundColor.GRAY
    private val BACKGROUND_GRAY_PRESSED = XBackgroundColor.GRAY_PRESSED
    private val BACKGROUND_THEME = XThemeColor.NORMAL
    private val BACKGROUND_THEME_PRESSED = XThemeColor.DEEP
    private val BACKGROUND_THEME_SWITCH = XThemeColor.DARK
    private val BORDER_GRAY = XBorderColor.GRAY_GRADIENT
    private val BORDER_WIDTH = XWidth.BORDER_M

    /**
     * 圆按钮
     * @param text 按钮文字
     * @param onClick 点击事件
     */
    @Composable
    fun Round(
        text: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (enabled) {
                if (isPressed.value) BACKGROUND_THEME_PRESSED else BACKGROUND_THEME
            } else {
                Color.DarkGray
            }

        Box(
            modifier = Modifier
                .clickVfx(interactionSource, enabled, onClick)
                .size(40.dp)
                // .padding(10.dp)
                .background(backgroundColor, CircleShape)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = text/*.take(1)*/,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.White else Color.LightGray
            )
        }
    }

    /**
     * 文本按钮
     *
     * @param text 按钮文字
     * @param enabled 是否可用
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Button(
        text: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (enabled) {
                if (isPressed.value) BACKGROUND_THEME_PRESSED else BACKGROUND_THEME
            } else {
                Color.DarkGray
            }

        Box(
            modifier = Modifier
                .clickVfx(interactionSource, enabled, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.White else Color.LightGray
            )
        }
    }

    /**
     * 文本按钮
     *
     * @param text 按钮文字
     * @param enabled 是否可用
     * @param modifier 修改器
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2025.9.6
     */
    @Composable
    fun Button(
        text: String,
        enabled: Boolean = true,
        modifier: Modifier,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (enabled) {
                if (isPressed.value) BACKGROUND_THEME_PRESSED else BACKGROUND_THEME
            } else {
                Color.DarkGray
            }

        Box(
            modifier = modifier
                .clickVfx(interactionSource, enabled, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.White else Color.LightGray
            )
        }
    }

    /**
     * 文本按钮
     *
     * @param text 按钮文字
     * @param color 颜色
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Button(
        text: String,
        color: List<Color>,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) color[1] else color[0]

        Box(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color[2]
            )
        }
    }

    /**
     * 图标按钮
     *
     * @param icon 图标
     * @param text 按钮文字
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Button(
        icon: Int,
        text: String,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BACKGROUND_THEME_PRESSED else BACKGROUND_THEME

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = Color.White,
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(5.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
                // color = ContentColor.DEFAULT_BROWN
            )
        }
    }

    /**
     * 图标按钮
     *
     * @param icon 图标
     * @param text 按钮文字
     * @param color 颜色
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Button(
        icon: Int,
        text: String,
        color: List<Color>,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) color[1] else color[0]

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = color[2],
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(5.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color[2]
            )
        }
    }

    /**
     * 图标按钮
     *
     * @param icon 图标
     * @param text 按钮文字
     * @param color 颜色
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Button(
        icon: Int,
        text: String,
        color: XButtonColor.ButtonColor,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) color.backgroundPressed else color.background

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentSize()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = color.content,
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(5.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color.content
            )
        }
    }

    /**
     * 双按钮
     *
     * @param iconOne 图标 1
     * @param iconTwo 图标 2
     * @param textOne 文字 1
     * @param textTwo 文字 2
     * @param onClickOne 点击事件 1
     * @param onClickTwo 点击事件 2
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Bibutton(
        iconOne: Int,
        iconTwo: Int,
        textOne: String,
        textTwo: String,
        onClickOne: () -> Unit,
        onClickTwo: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(icon = iconOne, text = textOne, onClick = onClickOne)

            Spacer(modifier = Modifier.width(10.dp))

            Button(icon = iconTwo, text = textTwo, onClick = onClickTwo)
        }
    }

    /**
     * 胶囊按钮
     *
     * @param image 图标
     * @param text 按钮文字
     * @param subText 副标题
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Capsule(
        image: Int,
        text: String,
        subText: String,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BACKGROUND_GRAY_PRESSED else BACKGROUND_GRAY

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentHeight()
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .border(
                    width = BORDER_WIDTH,
                    shape = RoundedCornerShape(50.dp),
                    brush = Brush.linearGradient(
                        BORDER_GRAY,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Text(
                    text = subText,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }

    /**
     * 胶囊按钮
     *
     * @param icon 图标
     * @param iconSize 图标大小
     * @param text 按钮文字
     * @param subText 副标题
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Capsule(
        icon: Int,
        iconSize: Int = 30,
        text: String,
        subText: String,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BACKGROUND_GRAY_PRESSED else BACKGROUND_GRAY

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentHeight()
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .border(
                    width = BORDER_WIDTH,
                    shape = RoundedCornerShape(50.dp),
                    brush = Brush.linearGradient(
                        BORDER_GRAY,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconPadding: Dp = if (iconSize >= 30) {
                0.dp
            } else if (iconSize == 20) {
                5.dp
            } else {
                ((30 - iconSize) / 2).dp
            }

            Image(
                painter = painterResource(id = icon),
                modifier = Modifier
                    .size(30.dp)
                    .padding(iconPadding),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Text(
                    text = subText,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }

    /**
     * 胶囊按钮
     *
     * @param icon 图标
     * @param iconSize 图标大小
     * @param text 按钮文字
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Capsule(
        icon: Int,
        iconSize: Int = 30,
        text: String,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BACKGROUND_GRAY_PRESSED else BACKGROUND_GRAY

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentHeight()
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .border(
                    width = BORDER_WIDTH,
                    shape = RoundedCornerShape(50.dp),
                    brush = Brush.linearGradient(
                        BORDER_GRAY,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconPadding: Dp = if (iconSize >= 30) {
                0.dp
            } else if (iconSize == 20) {
                5.dp
            } else {
                ((30 - iconSize) / 2).dp
            }

            Image(
                painter = painterResource(id = icon),
                modifier = Modifier
                    .size(30.dp)
                    .padding(iconPadding),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1
            )
        }
    }

    /**
     * 卡片按钮
     *
     * @param icon 图标
     * @param text 按钮文字
     * @param cardSize 卡片大小
     * @param iconSize 图标大小
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Card(
        icon: Int,
        text: String,
        cardSize: Int = 85,
        iconSize: Int = 30,
        onClick: () -> Unit,
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (isPressed.value) BACKGROUND_GRAY_PRESSED else BACKGROUND_GRAY

        Box(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .size(cardSize.dp)
                .background(backgroundColor, RoundedCornerShape(15.dp))
                .border(
                    width = BORDER_WIDTH,
                    shape = RoundedCornerShape(15.dp),
                    brush = Brush.linearGradient(
                        BORDER_GRAY,
                        start = Offset.Infinite,
                        end = Offset.Zero
                    )
                )
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(iconSize.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
        }
    }

    /**
     * 切换按钮
     *
     * @param icon 图标
     * @param iconSize 图标大小
     * @param text 按钮文字
     * @param subText 副文本
     * @param status 状态
     * @param onClick 点击事件
     *
     * Created by Wu Qizhen on 2024.6.16
     * Updated by Wu Qizhen on 2024.8.31
     * Updated by Wu Qizhen on 2024.11.30
     * Updated by Wu Qizhen on 2024.12.31
     * Updated by Wu Qizhen on 2025.7.23
     */
    @Composable
    fun Switch(
        icon: Int,
        iconSize: Int = 30,
        text: String,
        subText: String,
        status: State<Boolean>,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        // val isPressed = interactionSource.collectIsPressedAsState()
        val backgroundColor =
            if (status.value) BACKGROUND_THEME_SWITCH else BACKGROUND_GRAY
        val borderColor by animateColorAsState(
            targetValue = if (status.value) BACKGROUND_THEME else Color(
                23,
                23,
                23,
                255
            ), label = ""
        )
        val borderWidth by animateDpAsState(
            targetValue = if (status.value) 2.0f.dp else BORDER_WIDTH,
            label = ""
        )

        Row(
            modifier = Modifier
                .clickVfx(interactionSource, true, onClick)
                .wrapContentHeight()
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(50.dp))
                .border(
                    width = borderWidth,
                    shape = RoundedCornerShape(50.dp),
                    color = borderColor
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconPadding: Dp = if (iconSize >= 30) {
                0.dp
            } else if (iconSize == 20) {
                5.dp
            } else {
                ((30 - iconSize) / 2).dp
            }

            Image(
                painter = painterResource(id = icon),
                modifier = Modifier
                    .size(30.dp)
                    .padding(iconPadding),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Text(
                    text = subText,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}

/*
@preview
@Composable
fun XItemPreview() {
    XTheme {
        Column {
            Text(text = "普通按钮")

            Spacer(modifier = Modifier.height(10.dp))

            XItem.Button(text = "Text Button") { }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "胶囊按钮")

            Spacer(modifier = Modifier.height(10.dp))

            XItem.Capsule(
                image = R.drawable.logo_code_intellix,
                text = "Wu Qizhen",
                subText = "Developer"
            ) { }

            Spacer(modifier = Modifier.height(10.dp))

            XItem.Capsule(
                icon = R.drawable.logo_aethex_matrix,
                iconSize = 20,
                text = "Released 1.0.0",
                subText = "Version"
            ) { }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "卡片按钮")

            Spacer(modifier = Modifier.height(10.dp))

            XItem.Card(icon = R.drawable.logo_aethex_matrix, text = "Option") {}
        }
    }
}*/