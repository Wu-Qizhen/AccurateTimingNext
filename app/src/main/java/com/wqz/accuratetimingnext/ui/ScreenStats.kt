package com.wqz.accuratetimingnext.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * 判断是否是圆屏
 * Created by Wu Qizhen on 2024.7.11
 */
@Composable
fun isRound() =
    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)*/
    LocalConfiguration.current.isScreenRound /*else false*/