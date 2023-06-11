package com.example.quizler.ui.screen.onboarding

import android.content.Context
import com.example.domain.model.OnboardingState
import com.example.domain.usecase.ICacheTokenUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn

interface IOnboardingManager {
    suspend fun checkOnboardingState(): OnboardingState
}

class OnboardingManager(
    private val context: Context,
    private val cacheToken: ICacheTokenUseCase
) : IOnboardingManager {

    override suspend fun checkOnboardingState(): OnboardingState {
        val googleAccount = GoogleSignIn.getLastSignedInAccount(context)

        return googleAccount?.idToken?.let { token ->
            val isCachedToken = cacheToken(token)

            if (isCachedToken)
                OnboardingState.GotoSplash
            else
                OnboardingState.GotoSignIn
        } ?: OnboardingState.GotoSignIn
    }
}
