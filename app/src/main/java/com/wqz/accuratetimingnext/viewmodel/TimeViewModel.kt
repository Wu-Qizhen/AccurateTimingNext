package com.wqz.accuratetimingnext.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wqz.accuratetimingnext.database.entity.Time
import com.wqz.accuratetimingnext.repository.TimeRepository
import com.wqz.accuratetimingnext.viewmodel.state.TimeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 时间列表视图模型
 * Created by Wu Qizhen on 2025.7.3
 */
/*
@HiltViewModel
class TimeViewModel @Inject constructor(
    private val repository: TimeRepository
) : ViewModel() {

    // UI 状态管理
    sealed class TimeUiState {
        object Loading : TimeUiState()
        data class Success(
            val allTimes: List<Time> = emptyList(),
            val searchResults: List<Time> = emptyList(),
            val currentTime: Time? = null,
            val timeName: String? = null
        ) : TimeUiState()

        data class Error(val message: String) : TimeUiState()
    }

    // 统一状态管理
    private val _uiState = MutableStateFlow<TimeUiState>(TimeUiState.Loading)
    val uiState: StateFlow<TimeUiState> = _uiState.asStateFlow()

    init {
        loadAllTimes()
    }

    private fun loadAllTimes() {
        viewModelScope.launch {
            try {
                repository.allTimes.collect { times ->
                    _uiState.value = TimeUiState.Success(allTimes = times)
                }
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("加载数据失败: ${e.message}")
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            // 清空搜索结果
            _uiState.value = when (val current = _uiState.value) {
                is TimeUiState.Success -> current.copy(searchResults = emptyList())
                else -> current
            }
            return
        }

        viewModelScope.launch {
            try {
                repository.search(query).collect { results ->
                    _uiState.value = when (val current = _uiState.value) {
                        is TimeUiState.Success -> current.copy(searchResults = results)
                        else -> TimeUiState.Success(searchResults = results)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("搜索失败: ${e.message}")
            }
        }
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            try {
                val time = repository.getById(id)
                _uiState.value = when (val current = _uiState.value) {
                    is TimeUiState.Success -> current.copy(currentTime = time)
                    else -> TimeUiState.Success(currentTime = time)
                }
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("获取详情失败: ${e.message}")
            }
        }
    }

    fun getTimeNameById(id: Int) {
        viewModelScope.launch {
            try {
                val timeName = repository.getTimeNameById(id)
                _uiState.value = when (val current = _uiState.value) {
                    is TimeUiState.Success -> current.copy(timeName = timeName)
                    else -> TimeUiState.Success(timeName = timeName)
                }
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("获取详情失败: ${e.message}")
            }
        }
    }

    fun add(timeName: String, expectedTime: List<Long>) {
        viewModelScope.launch {
            try {
                val newTime = Time(timeName = timeName, expectedTimes = expectedTime)
                repository.insert(newTime)
                // 添加成功后刷新列表
                loadAllTimes()
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("添加失败: ${e.message}")
            }
        }
    }

    fun updateTimeName(id: Int, newName: String) {
        viewModelScope.launch {
            try {
                repository.updateTimeName(id, newName)
                // 更新后刷新数据
                loadAllTimes()
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("更新名称失败: ${e.message}")
            }
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteById(id)
                // 删除后刷新数据
                loadAllTimes()
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("删除失败: ${e.message}")
            }
        }
    }

    fun delete(time: Time) {
        viewModelScope.launch {
            try {
                repository.delete(time)
                // 删除后刷新数据
                loadAllTimes()
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error("删除失败: ${e.message}")
            }
        }
    }
}*/

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val repository: TimeRepository
) : ViewModel() {

    private val _searchTerm = MutableStateFlow("")
    val searchTerm: StateFlow<String> = _searchTerm.asStateFlow()

    // 添加当前时间状态
    private val _currentTime = MutableStateFlow<Time?>(null)
    val currentTime: StateFlow<Time?> = _currentTime.asStateFlow()

    // 合并全部时间和搜索结果
    @OptIn(ExperimentalCoroutinesApi::class)
    val times: StateFlow<List<Time>> = searchTerm.flatMapLatest { term ->
        if (term.isBlank()) {
            repository.allTimes
        } else {
            repository.search("%$term%")  // 添加通配符实现模糊查询
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // UI状态（加载/错误）
    private val _uiState = MutableStateFlow<TimeUiState>(TimeUiState.Idle)
    val uiState: StateFlow<TimeUiState> = _uiState.asStateFlow()

    // 操作：更新搜索词
    fun setSearchTerm(term: String) {
        _searchTerm.value = term
    }

    // 操作：插入时间
    fun insert(time: Time) {
        viewModelScope.launch {
            _uiState.value = TimeUiState.Loading
            try {
                repository.insert(time)
                _uiState.value = TimeUiState.Success("添加成功")
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error(e.message ?: "添加失败")
            }
        }
    }

    fun insert(timeName: String, expectedTimes: List<Long>) {
        viewModelScope.launch {
            _uiState.value = TimeUiState.Loading
            try {
                repository.insert(Time(timeName = timeName, expectedTimes = expectedTimes))
                _uiState.value = TimeUiState.Success("添加成功")
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error(e.message ?: "添加失败")
            }
        }
    }

    // 操作：更新时间对象
    fun update(time: Time) {
        viewModelScope.launch {
            _uiState.value = TimeUiState.Loading
            try {
                repository.update(time)
                _uiState.value = TimeUiState.Success("更新成功")
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error(e.message ?: "更新失败")
            }
        }
    }

    // 操作：更新时间名称
    fun updateTimeName(id: Int, newName: String) {
        viewModelScope.launch {
            _uiState.value = TimeUiState.Loading
            try {
                repository.updateTimeName(id, newName)
                _uiState.value = TimeUiState.Success("名称更新成功")
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error(e.message ?: "名称更新失败")
            }
        }
    }

    // 操作：删除时间
    fun deleteById(id: Int) {
        viewModelScope.launch {
            _uiState.value = TimeUiState.Loading
            try {
                repository.deleteById(id)
                _uiState.value = TimeUiState.Success("删除成功")
            } catch (e: Exception) {
                _uiState.value = TimeUiState.Error(e.message ?: "删除失败")
            }
        }
    }

    /*// 操作：获取单个时间（用于编辑）
    suspend fun getById(id: Int): Time? {
        return try {
            repository.getById(id)
        } catch (e: Exception) {
            _uiState.value = TimeUiState.Error("获取数据失败")
            null
        }
    }*/

    // 修改 getById 方法，更新 currentTime 状态
    suspend fun getById(id: Int) {
        try {
            _currentTime.value = repository.getById(id)
        } catch (e: Exception) {
            _uiState.value = TimeUiState.Error("获取数据失败")
            _currentTime.value = null
        }
    }
}