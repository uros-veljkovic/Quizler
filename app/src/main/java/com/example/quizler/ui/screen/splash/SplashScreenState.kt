package com.example.quizler.ui.screen.splash

import com.example.quizler.model.InfoBannerData

data class SplashScreenState(
    val progress: Float = 0.0f,
    val isProgressVisible: Boolean = true,
    val isGoToHomeScreen: Boolean = false,
    val infoBannerData: InfoBannerData? = InfoBannerData.Loading,
    val hasConnection: Boolean = true,
    val isDataFetchInProgress: Boolean = false,
)
