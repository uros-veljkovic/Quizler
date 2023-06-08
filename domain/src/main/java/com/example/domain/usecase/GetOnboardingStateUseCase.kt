package com.example.domain.usecase

import com.example.domain.model.OnboardingState

interface IGetOnboardingStateUseCase {
    operator fun invoke(): OnboardingState
}

class GetOnboardingStateUseCase : IGetOnboardingStateUseCase {

    override operator fun invoke(): OnboardingState {
        return OnboardingState.GotoSignIn
    }
}