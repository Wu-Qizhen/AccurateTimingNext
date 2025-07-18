package com.wqz.accuratetimingnext.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wqz.accuratetimingnext.database.entity.GameRecord
import com.wqz.accuratetimingnext.repository.GameRecordRepository
import com.wqz.accuratetimingnext.viewmodel.state.GameRecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

/**
 * 竞速记录
 * Created by Wu Qizhen on 2025.7.15
 */
@HiltViewModel
class GameRecordViewModel @Inject constructor(
    private val repository: GameRecordRepository
) : ViewModel() {

    // 当前选择的月份
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    // val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    // 按月筛选的游戏记录
    @OptIn(ExperimentalCoroutinesApi::class)
    val monthlyRecords: StateFlow<List<GameRecord>> = _currentMonth.flatMapLatest { yearMonth ->
        // 将 YearMonth 转换为时间戳范围
        val (start, end) = convertMonthToTimestampRange(yearMonth)
        repository.getByDateRange(start, end)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // 所有游戏记录的流
    /*val allGameRecords: StateFlow<List<GameRecord>> = repository.allGameRecords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )*/

    // UI状态管理
    private val _uiState = MutableStateFlow<GameRecordUiState>(GameRecordUiState.Idle)
    val uiState: StateFlow<GameRecordUiState> = _uiState.asStateFlow()

    // 当前选中的游戏记录
    private val _currentGameRecord = MutableStateFlow<GameRecord?>(null)
    val currentGameRecord: StateFlow<GameRecord?> = _currentGameRecord.asStateFlow()

    // 设置当前月份
    fun setMonth(month: YearMonth) {
        _currentMonth.value = month
    }

    // 插入新记录
    fun insert(gameRecord: GameRecord) {
        viewModelScope.launch {
            _uiState.value = GameRecordUiState.Loading
            try {
                repository.insert(gameRecord)
                _uiState.value = GameRecordUiState.Success("记录添加成功")
            } catch (e: Exception) {
                _uiState.value = GameRecordUiState.Error(e.message ?: "添加记录失败")
            }
        }
    }

    // 删除记录
    fun deleteById(id: Int) {
        viewModelScope.launch {
            _uiState.value = GameRecordUiState.Loading
            try {
                repository.delete(id)
                _uiState.value = GameRecordUiState.Success("记录删除成功")
            } catch (e: Exception) {
                _uiState.value = GameRecordUiState.Error(e.message ?: "删除记录失败")
            }
        }
    }

    // 获取指定记录
    suspend fun getById(id: Int) {
        try {
            _currentGameRecord.value = repository.getById(id)
        } catch (_: Exception) {
            _uiState.value = GameRecordUiState.Error("获取记录详情失败")
            _currentGameRecord.value = null
        }
    }

    // 重置当前选中的记录
    fun resetCurrentRecord() {
        _currentGameRecord.value = null
    }

    // 辅助函数：将月份转换为时间戳范围
    private fun convertMonthToTimestampRange(month: YearMonth): Pair<Long, Long> {
        val startDate = month.atDay(1).atStartOfDay(ZoneId.systemDefault())
        val endDate = month.plusMonths(1).atDay(1).atStartOfDay(ZoneId.systemDefault())

        return startDate.toInstant().toEpochMilli() to endDate.toInstant().toEpochMilli()
    }
}