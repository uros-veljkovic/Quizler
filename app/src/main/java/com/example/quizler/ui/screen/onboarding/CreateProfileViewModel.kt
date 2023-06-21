package com.example.quizler.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.State
import com.example.domain.model.UserProfile
import com.example.domain.usecase.IUpdateCurrentUserProfileUseCase
import com.example.quizler.MainScreen
import com.example.quizler.model.InfoBannerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateProfileViewModel(
    private val updateCurrentUserProfileUseCase: IUpdateCurrentUserProfileUseCase
) : ViewModel() {

    private val _gotoNextScreen = MutableStateFlow("")
    val gotoNextScreen = _gotoNextScreen.asStateFlow()

    private val _state = MutableStateFlow(CreateProfileState())
    val state = _state.asStateFlow()

    fun setUsername(value: String) {
        _state.update { it.copy(username = value) }
    }

    fun setAvatar(avatar: Avatar) {
        _state.update { it.copy(choosenAvatar = avatar) }
    }

    fun onConfirmCreateProfile() {
        val username = _state.value.username
        val avatarName = _state.value.choosenAvatar?.name
        if (username.isNotEmpty() && avatarName.isNullOrEmpty().not()) {
            viewModelScope.launch {
                val result = updateCurrentUserProfileUseCase.fetchAndCache(
                    isForceRefresh = true,
                    UserProfile(
                        username = username,
                        avatar = avatarName!!,
                        profileImageUrl = ""
                    )
                )
                if (result is State.Success) {
                    _gotoNextScreen.update { MainScreen.Splash.route }
                } else {
                    showErrorMessage()
                }
            }
        } else {
            showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        viewModelScope.launch {
            _state.update { it.copy(infoBannerData = InfoBannerData.CreateProfileError) }
            delay(5000)
            _state.update { it.copy(infoBannerData = null) }
        }
    }
}
