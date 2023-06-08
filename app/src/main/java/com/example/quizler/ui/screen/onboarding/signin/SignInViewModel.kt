package com.example.quizler.ui.screen.onboarding.signin

import androidx.lifecycle.ViewModel
import timber.log.Timber

class SignInViewModel : ViewModel() {

    fun onSignInSuccessful(email: String) {
        Timber.d("Log in successful! Email: $email")
    }

    fun onSignInFailed(reason: String) {
        Timber.d("Log in failed. Reason: $reason")
    }
}
