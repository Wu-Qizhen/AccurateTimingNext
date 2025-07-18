package com.wqz.accuratetimingnext.act.time.util

import android.annotation.SuppressLint

/**
 * 时间格式化工具类
 * Created by Wu Qizhen on 2025.7.4
 */
object TimeFormat {
    @SuppressLint("DefaultLocale")
    fun formatMilliseconds(milliseconds: Long): String {
        val seconds = (milliseconds / 1000) % 60
        val millis = milliseconds % 1000
        return String.format("%02d.%03d", seconds, millis)
    }
}