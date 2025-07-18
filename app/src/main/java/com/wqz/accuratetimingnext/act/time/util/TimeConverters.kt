package com.wqz.accuratetimingnext.act.time.util

/**
 * 时间转换工具类
 * Created by Wu Qizhen on 2025.7.3
 */
object TimeConverters {
    fun parseAndConvertTimes(input: String): List<Long> {
        // 1. 移除空行和空格，按换行符分割
        val lines = input.lines()
            .map { it.trim() } // 移除每行首尾空格
            .filter { it.isNotBlank() } // 过滤空行

        val validTimes = mutableListOf<Long>()

        for (line in lines) {
            try {
                // 2. 尝试解析为数字
                val number = line.toDoubleOrNull() ?: continue

                // 3. 转换时间单位并验证范围
                val milliseconds = when {
                    // 整数：视为毫秒
                    line.contains('.', ignoreCase = false).not() -> number.toLong()
                    // 小数：视为秒，转换为毫秒
                    else -> (number * 1000).toLong()
                }

                // 4. 验证时间范围 (0-120秒)
                if (milliseconds in 1..99_000) {
                    validTimes.add(milliseconds)
                }
            } catch (_: Exception) {
                // 忽略解析失败的项
                continue
            }
        }

        // 5. 排序并返回，超过十个仅返回前十个
        return validTimes.sorted().take(10)
    }
}