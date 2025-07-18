package com.wqz.accuratetimingnext.act.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * 秒表数据模型
 * Created by Wu Qizhen on 2025.7.3
 */
@Suppress("OPT_IN_USAGE")
class StopWatchViewModel : ViewModel() {
    private val _elapsedTime = MutableStateFlow(0L)

    // 暴露当前时间值用于误差计算
    val currentTimeMillis: StateFlow<Long> = _elapsedTime.asStateFlow()

    private val _timerState = MutableStateFlow(TimerState.RESET)
    val timerState = _timerState.asStateFlow()

    private val formatter = DateTimeFormatter.ofPattern("ss.SSS")

    val stopWatchText = _elapsedTime
        .map {
            LocalTime.ofNanoOfDay(it * 1_000_000).format(formatter)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "00.000"
        )

    init {
        _timerState.flatMapLatest { state ->
            getTimerFlow(isRunning = state == TimerState.RUNNING)
        }
            .onEach { timeDiff ->
                _elapsedTime.update { it + timeDiff }
            }
            .launchIn(viewModelScope)
    }

    fun toggleIsRunning() {
        when (timerState.value) {
            TimerState.RUNNING -> _timerState.update { TimerState.PAUSED }
            TimerState.PAUSED,
            TimerState.RESET -> _timerState.update { TimerState.RUNNING }
        }
    }

    fun resetTimer() {
        _timerState.update { TimerState.RESET }
        _elapsedTime.update { 0L }
    }

    private fun getTimerFlow(isRunning: Boolean): Flow<Long> {
        return flow {
            var startMills = System.currentTimeMillis()
            while (isRunning) {
                val currentMills = System.currentTimeMillis()
                val timeDiff = if (currentMills > startMills) {
                    currentMills - startMills
                } else {
                    0L
                }
                emit(timeDiff)
                startMills = System.currentTimeMillis()
                delay(10L)
            }
        }
    }
}