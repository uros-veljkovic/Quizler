package com.example.quizler.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class AppScreenState(
    val bottomNavigationConfig: BottomNavigationConfig = BottomNavigationConfig(),
    val navigate: Pair<Screen, Screen>? = null,
    val shouldCloseApp: Boolean = false,
    val isExitDialogVisible: Boolean = false
)
