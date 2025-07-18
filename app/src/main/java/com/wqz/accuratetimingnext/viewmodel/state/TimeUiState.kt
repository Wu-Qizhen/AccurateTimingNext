package com.wqz.accuratetimingnext.viewmodel.state

/**
 * 状态密封类
 * Created by Wu Qizhen on 2025.7.3
 */
sealed class TimeUiState {
    object Idle : TimeUiState()
    object Loading : TimeUiState()
    data class Success(val message: String) : TimeUiState()
    data class Error(val message: String) : TimeUiState()
}