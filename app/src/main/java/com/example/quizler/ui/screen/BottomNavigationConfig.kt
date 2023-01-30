package com.example.quizler.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.quizler.ui.components.BottomNavigationItem

@Stable
@Immutable
data class BottomNavigationConfig(
    val itemSelected: BottomNavigationItem = BottomNavigationItem.Home,
    val isBottomNavVisible: Boolean = false,
)
