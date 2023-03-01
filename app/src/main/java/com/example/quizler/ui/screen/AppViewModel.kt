package com.example.quizler.ui.screen

import androidx.lifecycle.ViewModel
import com.example.quizler.ui.components.BottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow(AppScreenState())

    fun setBottomNavVisible(visible: Boolean) {
        state.update {
            it.copy(
                bottomNavigationConfig = it.bottomNavigationConfig.copy(
                    isBottomNavVisible = visible
                )
            )
        }
    }

    fun setBottomNavItem(item: BottomNavigationItem) {
        val screen = when (item) {
            BottomNavigationItem.Home -> Screen.Home
            BottomNavigationItem.Scoreboard -> Screen.Scoreboard
            BottomNavigationItem.NewQuestion -> Screen.NewQuestion
        }
        state.update {
            it.copy(
                bottomNavigationConfig = it.bottomNavigationConfig.copy(itemSelected = item),
                navigate = screen
            )
        }
    }

    fun closeApp() {
        state.update { it.copy(shouldCloseApp = true) }
    }

    fun handleBack() {
        state.update {
            it.copy(
                isExitDialogVisible = true,
                bottomNavigationConfig = it.bottomNavigationConfig.copy(isBottomNavVisible = false)
            )
        }
    }

    fun handleExitDialogDecline() {
        state.update {
            it.copy(
                isExitDialogVisible = false,
                bottomNavigationConfig = it.bottomNavigationConfig.copy(isBottomNavVisible = true)
            )
        }
    }
}
