package com.example.quizler.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class AppScreenState(
    val bottomNavigationConfig: BottomNavigationConfig = BottomNavigationConfig(),
    val shouldCloseApp: Boolean = false,
    val isExitDialogVisible: Boolean = false
)
