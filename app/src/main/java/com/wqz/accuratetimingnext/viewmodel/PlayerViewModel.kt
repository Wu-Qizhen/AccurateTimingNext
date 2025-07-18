package com.wqz.accuratetimingnext.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wqz.accuratetimingnext.database.entity.Player
import com.wqz.accuratetimingnext.repository.PlayerRepository
import com.wqz.accuratetimingnext.viewmodel.state.PlayerUiState
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
 * 玩家视图模型
 * Created by Wu Qizhen on 2025.7.3
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _searchTerm = MutableStateFlow("")
    val searchTerm: StateFlow<String> = _searchTerm.asStateFlow()

    // 当前选中的玩家
    private val _currentPlayer = MutableStateFlow<Player?>(null)
    val currentPlayer: StateFlow<Player?> = _currentPlayer.asStateFlow()

    // 合并全部玩家和搜索结果
    @OptIn(ExperimentalCoroutinesApi::class)
    val players: StateFlow<List<Player>> = searchTerm.flatMapLatest { term ->
        if (term.isBlank()) {
            repository.allPlayers
        } else {
            repository.search("%$term%")  // 添加通配符实现模糊查询
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // UI 状态（加载/错误）
    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Idle)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    // 操作：更新搜索词
    fun setSearchTerm(term: String) {
        _searchTerm.value = term
    }

    // 操作：插入玩家
    fun insert(player: Player) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                repository.insert(player)
                _uiState.value = PlayerUiState.Success("添加成功")
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(e.message ?: "添加失败")
            }
        }
    }

    fun insert(name: String) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                val newPlayer = Player(playerName = name)
                repository.insert(newPlayer)
                _uiState.value = PlayerUiState.Success("添加成功")
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(e.message ?: "添加失败")
            }
        }
    }

    // 操作：更新玩家
    fun update(player: Player) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                repository.update(player)
                _uiState.value = PlayerUiState.Success("更新成功")
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(e.message ?: "更新失败")
            }
        }
    }

    // 操作：删除玩家
    fun delete(player: Player) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                repository.delete(player)
                _uiState.value = PlayerUiState.Success("删除成功")
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(e.message ?: "删除失败")
            }
        }
    }

    // 操作：删除时间
    fun deleteById(id: Int) {
        viewModelScope.launch {
            _uiState.value = PlayerUiState.Loading
            try {
                repository.deleteById(id)
                _uiState.value = PlayerUiState.Success("删除成功")
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error(e.message ?: "删除失败")
            }
        }
    }

    // 操作：获取单个玩家（用于编辑）
    /*suspend fun getById(id: Int) {
        try {
            _currentPlayer.value = repository.getById(id)
        } catch (e: Exception) {
            _uiState.value = PlayerUiState.Error("获取数据失败")
            _currentPlayer.value = null
        }
    }*/
    suspend fun getById(id: Int) {
        _uiState.value = PlayerUiState.Loading
        try {
            val player = repository.getById(id)
            if (player != null) {
                _currentPlayer.value = player
                _uiState.value = PlayerUiState.Idle
            } else {
                _uiState.value = PlayerUiState.Error("未找到玩家")
            }
        } catch (e: Exception) {
            _uiState.value = PlayerUiState.Error(e.message ?: "获取数据失败")
            _currentPlayer.value = null
        }
    }
}