package com.example.quizler.ui.screen

import androidx.lifecycle.ViewModel
import com.example.quizler.ui.components.BottomNavigationItems
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

    fun setBottomNavItem(item: BottomNavigationItems) {
        val route = when (item) {
            BottomNavigationItems.Home -> Pair(Screen.Scoreboard, Screen.Home)
            BottomNavigationItems.Scoreboard -> Pair(Screen.Home, Screen.Scoreboard)
        }
        state.update {
            it.copy(
                bottomNavigationConfig = it.bottomNavigationConfig.copy(itemSelected = item),
                navigate = route
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
