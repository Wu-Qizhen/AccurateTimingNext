package com.wqz.accuratetimingnext.act.player.util

/**
 * 玩家校验工具类
 * Created by Wu Qizhen on 2025.7.3
 */
object PlayerValidator {
    /**
     * 校验玩家名称是否符合规则
     *
     * @param name 待校验的名称
     * @return true（合法），false（非法）
     */
    fun isValidPlayerName(name: String): Boolean {
        val regex = """^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$""".toRegex()
        return regex.matches(name)
    }
}