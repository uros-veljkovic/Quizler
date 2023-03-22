package com.example.quizler.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AppScreenState()
    )

    fun setBottomNavVisible(visible: Boolean) {
        _state.update {
            it.copy(
                bottomNavigationConfig = it.bottomNavigationConfig.copy(
                    isBottomNavVisible = visible
                )
            )
        }
    }

    fun closeApp() {
        _state.update { it.copy(shouldCloseApp = true) }
    }

    fun handleBack() {
        _state.update {
            it.copy(
                isExitDialogVisible = true,
                bottomNavigationConfig = it.bottomNavigationConfig.copy(isBottomNavVisible = false)
            )
        }
    }

    fun handleExitDialogDecline() {
        _state.update {
            it.copy(
                isExitDialogVisible = false,
                bottomNavigationConfig = it.bottomNavigationConfig.copy(isBottomNavVisible = true)
            )
        }
    }
}
