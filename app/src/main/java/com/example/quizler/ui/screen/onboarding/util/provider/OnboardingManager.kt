package com.example.quizler.ui.screen.onboarding.util.provider

import android.content.Context
import com.example.domain.model.OnboardingState
import com.google.android.gms.auth.api.signin.GoogleSignIn

interface IOnboardingManager {
    operator fun invoke(): OnboardingState
}

class OnboardingManager(
    private val context: Context
) : IOnboardingManager {

    override operator fun invoke(): OnboardingState {
        val googleAccount = GoogleSignIn.getLastSignedInAccount(context)
        return if (googleAccount == null)
            OnboardingState.GotoSignIn
        else
            OnboardingState.GotoSplash
    }
}
