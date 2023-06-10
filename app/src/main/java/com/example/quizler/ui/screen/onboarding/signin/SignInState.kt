package com.example.quizler.ui.screen.onboarding.signin

import com.example.quizler.model.InfoBannerData

data class SignInState(
    val nextScreen: String? = null,
    val infoBannerData: InfoBannerData? = null,
)