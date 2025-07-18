package com.wqz.accuratetimingnext.act.time.util

import kotlin.random.Random

/**
 * 随机生成不重复的时间列表（单位：毫秒）
 * Created by Wu Qizhen on 2025.7.3
 */
object RandomUniqueTimeGenerator {

    /**
     * 随机生成不重复的时间列表（单位：毫秒）
     *
     * @param count 生成时间的数量
     * @param max   可生成时间的最大值（单位：毫秒）
     * @return 包含随机生成的不重复时间的列表（单位：毫秒）
     */
    fun generateRandomUniqueTimes(count: Int, max: Long): List<Long> {
        // 检查 count 是否在 0 到 10 之间
        require(count in 0..10) { "数量必须在 0 到 10 之间" }

        // 确定最终生成的元素数量
        val effectiveCount = if (count == 0) {
            // 如果 count 为 0，则随机选择 4 到 10 之间的数量
            Random.nextInt(4, 11)
        } else {
            count
        }

        // 检查 max 是否足够生成 effectiveCount 个唯一时间值
        require(max >= 10L * effectiveCount) {
            "最大值至少应为 10L * $effectiveCount = ${10L * effectiveCount}"
        }

        val uniqueTimes = mutableSetOf<Long>()
        val randomTimes = mutableListOf<Long>()
        val random = Random.Default

        while (randomTimes.size < effectiveCount) {
            // 生成 [10, max] 范围内的随机毫秒值
            val candidate = random.nextLong(10, max + 1)

            if (!uniqueTimes.contains(candidate)) {
                uniqueTimes.add(candidate)
                randomTimes.add(candidate)
            }
        }

        // 排序
        return randomTimes.sorted()
    }
}