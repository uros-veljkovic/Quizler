package com.example.quizler.ui.screen.onboarding.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.model.InfoBannerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

data class SignInState(
    val infoBannerData: InfoBannerData? = null,
)

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asSharedFlow()

    fun onSignInSuccessful(email: String) {
        Timber.d("Log in successful! Email: $email")
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
