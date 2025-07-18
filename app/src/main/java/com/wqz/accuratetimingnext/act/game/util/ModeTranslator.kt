package com.wqz.accuratetimingnext.act.game.util

/**
 * 模式翻译
 * Created by Wu Qizhen on 2025.7.15
 */
object ModeTranslator {
    fun getChineseName(mode: Mode): String {
        return when (mode) {
            Mode.RACING_MODE -> "竞赛记录模式"
            // Mode.PRACTICE_MODE -> "经典练习模式"
            Mode.BLIND_MODE -> "盲卡挑战模式"
            Mode.MEMORY_MODE -> "记忆挑战模式"
            Mode.REVERSE_MODE -> "反向挑战模式"
            Mode.DISORDERED_MEMORY_MODE -> "乱序记忆模式"
            Mode.COMPUTATIONAL_MODE -> "计算挑战模式"
            Mode.EXPERT_MODE -> "专家模式"
            // 添加默认分支确保覆盖所有情况
            else -> "竞赛记录模式"
        }
    }
}