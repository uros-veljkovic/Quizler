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

    fun closeApp() {
        _state.update { it.copy(shouldCloseApp = true) }
    }

    fun handleBack() {
        _state.update {
            it.copy(
                isExitDialogVisible = true,
            )
        }
    }

    fun handleExitDialogDecline() {
        _state.update {
            it.copy(
                isExitDialogVisible = false,
            )
        }
    }
}
