package com.wqz.accuratetimingnext.viewmodel.state

/**
 * 状态密封类
 * Created by Wu Qizhen on 2025.7.15
 */
sealed class GameRecordUiState {
    object Idle : GameRecordUiState()
    object Loading : GameRecordUiState()
    data class Success(val message: String) : GameRecordUiState()
    data class Error(val error: String) : GameRecordUiState()
}