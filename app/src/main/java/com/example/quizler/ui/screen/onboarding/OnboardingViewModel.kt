package com.example.quizler.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OnboardingState
import com.example.domain.usecase.IGetOnboardingStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val getOnboardingState: IGetOnboardingStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingScreenState())
    val state = _state.asStateFlow()

    private val _gotoSplashScreen = MutableSharedFlow<Unit>()
    val gotoSplashScreen = _gotoSplashScreen.asSharedFlow()

    init {
        checkIfSignedIn()
    }

    private fun checkIfSignedIn() {
        viewModelScope.launch {
            when (getOnboardingState()) {
                OnboardingState.GotoSignIn -> _state.update { it.copy(visibleScreen = OnboardingNavigation.SignIn) }
                OnboardingState.GotoCreateProfileDetails -> _state.update { it.copy(visibleScreen = OnboardingNavigation.CreateProfileDetails) }
                OnboardingState.GotoSplash -> _gotoSplashScreen.emit(Unit)
            }
        }
    }
}
