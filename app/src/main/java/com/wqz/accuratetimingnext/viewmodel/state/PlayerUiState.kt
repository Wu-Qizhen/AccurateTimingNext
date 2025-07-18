package com.wqz.accuratetimingnext.viewmodel.state

/**
 * 玩家 UI 状态密封类
 * Created by Wu Qizhen on 2025.7.4
 */
sealed class PlayerUiState {
    object Idle : PlayerUiState()
    object Loading : PlayerUiState()
    data class Success(val message: String) : PlayerUiState()
    data class Error(val message: String) : PlayerUiState()
}