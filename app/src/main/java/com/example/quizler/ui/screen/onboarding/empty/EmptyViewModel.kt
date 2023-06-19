package com.example.quizler.ui.screen.onboarding.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FirstDestination
import com.example.domain.usecase.IDetermainNextDestinationScreenUseCase
import com.example.quizler.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmptyViewModel(
    private val determainFirstDestinationScreen: IDetermainNextDestinationScreenUseCase
) : ViewModel() {

    private val _nextScreen = MutableStateFlow("")
    val nextScreen = _nextScreen.asStateFlow()

    init {
        gotoNextScreen()
    }

    private fun gotoNextScreen() {
        viewModelScope.launch {
            val route = when (determainFirstDestinationScreen()) {
                FirstDestination.SignIn -> MainScreen.SignIn.route
                FirstDestination.CreateProfile -> MainScreen.CreateProfile.route
                FirstDestination.Splash -> MainScreen.Splash.route
            }
            _nextScreen.emit(route)
        }
    }
}
