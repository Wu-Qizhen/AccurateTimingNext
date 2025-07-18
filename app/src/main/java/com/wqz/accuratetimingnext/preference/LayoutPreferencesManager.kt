package com.wqz.accuratetimingnext.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 布局类型管理器
 * Created by Wu Qizhen on 2024.12.31
 */
class LayoutPreferencesManager(context: Context) {
    companion object {
        // 布局类型常量
        private const val PREF_KEY = "layout"
        private const val PREF_NAME = "LayoutPrefs" // 自定义 SharedPreferences 名称
        const val LAYOUT_TYPE_GRID = "GRID"
        const val LAYOUT_TYPE_LIST = "LIST"
    }

    // 使用 Context.getSharedPreferences 替代 PreferenceManager
    private val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    init {
        initializeDefaults()
    }

    private fun initializeDefaults() {
        // 如果 PREF_KEY 不存在，设置默认值为 LAYOUT_TYPE_LIST
        if (!sharedPreferences.contains(PREF_KEY)) {
            sharedPreferences.edit { putString(PREF_KEY, LAYOUT_TYPE_LIST) }
        }
    }

    // 设置布局类型
    fun setLayoutType(layoutType: String) {
        sharedPreferences.edit { putString(PREF_KEY, layoutType) }
    }

    // 获取布局类型
    private fun getLayoutType(): String =
        sharedPreferences.getString(PREF_KEY, LAYOUT_TYPE_LIST) ?: LAYOUT_TYPE_LIST

    // 检查当前布局是否为网格布局
    fun isGridLayout(): Boolean = getLayoutType() == LAYOUT_TYPE_GRID
}