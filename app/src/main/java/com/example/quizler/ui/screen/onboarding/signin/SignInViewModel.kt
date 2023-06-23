package com.example.quizler.ui.screen.onboarding.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.State
import com.example.domain.model.FirstDestination
import com.example.domain.usecase.IDetermainNextDestinationScreenUseCase
import com.example.domain.usecase.ISignInUseCase
import com.example.quizler.MainScreen
import com.example.quizler.model.InfoBannerData
import com.example.quizler.utils.signin.manager.token.refresh.AuthenticationProviders
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(
    private val signIn: ISignInUseCase,
    private val determainNextDestinationScreen: IDetermainNextDestinationScreenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asSharedFlow()

    fun onGoogleSignInSuccessful(token: String) {
        viewModelScope.launch {
            val signInState = signIn(token, AuthenticationProviders.GOOGLE)
            if (signInState is State.Success) {
                val nextDestination = when (determainNextDestinationScreen()) {
                    FirstDestination.SignIn -> throw Exception("User preferences not stored properly")
                    FirstDestination.CreateProfile -> MainScreen.CreateProfile.route
                    FirstDestination.Splash -> MainScreen.Splash.route
                }
                goToRoute(nextDestination)
            } else {
                showErrorMessage()
            }
        }
    }

    private fun goToRoute(route: String) {
        _state.update { it.copy(nextScreen = route) }
    }

    fun onSignInFailed(reason: String) {
        viewModelScope.launch {
            Timber.d("Log in failed. Reason: $reason")
            showErrorMessage()
        }
    }

    private suspend fun showErrorMessage() {
        _state.update { it.copy(infoBannerData = InfoBannerData.SignInFailed) }
        delay(4000)
        _state.update { it.copy(infoBannerData = null) }
    }
}
