package com.example.quizler.ui.screen.splash

import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.util.State

data class SplashScreenState(
    val progress: Float = 0.0f,
    val isProgressVisible: Boolean = true,
    val isGoToHomeScreen: Boolean = false,
    val infoBannerData: InfoBannerData? = InfoBannerData.Loading,
    val hasConnection: Boolean = true,
    val isDataFetchInProgress: Boolean = false,
) {
    fun copyWithError(error: State.Error<*>): SplashScreenState {
        return this.copy(isProgressVisible = false, infoBannerData = error.getInfoBanner())
    }

    fun copyWithChangedNetworkConnectivity(hasConnectivity: Boolean): SplashScreenState {
        return copy(hasConnection = hasConnectivity)
    }
}
