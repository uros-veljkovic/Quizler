package com.example.quizler.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.quizler.ui.components.BottomNavigationItems

@Stable
@Immutable
data class BottomNavigationConfig(
    val itemSelected: BottomNavigationItems = BottomNavigationItems.Home,
    val isBottomNavVisible: Boolean = false,
)
