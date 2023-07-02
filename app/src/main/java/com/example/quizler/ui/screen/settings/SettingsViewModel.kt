package com.example.quizler.ui.screen.settings

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ISignOutUseCase
import com.example.quizler.model.InfoBannerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val shareQuizLink: IShareQuizlerLinkManager,
    private val appReviewHandler: IAppReviewHandler,
    private val emailManager: IEmailManager,
    private val webPageOpener: IWebPageOpener,
    private val signOutUseCase: ISignOutUseCase,
) : ViewModel() {

    private val _signOut = MutableSharedFlow<Unit>()
    val signOut = _signOut.asSharedFlow()

    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    fun onSettingsItemClicked(event: SettingsItemEvent, context: Context) {
        when (event) {
            SettingsItemEvent.Personalize -> {}
            SettingsItemEvent.PrivacePolicy -> webPageOpener.openWebPage(context, KVIZLER_PRIVACY_POLICY_URL)
            SettingsItemEvent.RateApp -> onRateApp(context as Activity)
            SettingsItemEvent.Share -> onShareQuizler(context)
            is SettingsItemEvent.WriteEmail -> emailManager.writeEmailTo("urosveljkovic@yahoo.com", context)
        }
    }

    private fun onAppRated() {
        viewModelScope.launch {
            _state.update { it.copy(InfoBannerData.SuccessfullyCreatedNewQuestion) }
            delay(5000)
            _state.update { it.copy(infoBannerData = null) }
        }
    }

    private fun onShareQuizler(context: Context) {
        shareQuizLink.shareLink(context)
    }

    private fun onRateApp(activity: Activity) {
        appReviewHandler.launchReviewFlow(activity, viewModelScope)
    }

    fun onSignOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                signOutUseCase.invoke()
            }
            _signOut.emit(Unit)
        }
    }

    companion object {
        const val KVIZLER_PRIVACY_POLICY_URL =
            "https://github.com/urkeev14/Quizler/wiki/Privacy-Policy-for-Kvizler-on-Google-Play-Store"
    }
}