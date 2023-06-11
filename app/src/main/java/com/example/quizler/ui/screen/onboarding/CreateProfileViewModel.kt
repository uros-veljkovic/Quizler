package com.example.quizler.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.model.InfoBannerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(CreateProfileState())
    val state = _state.asStateFlow()

    fun setUsername(value: String) {
        _state.update { it.copy(username = value) }
    }

    fun setAvatar(avatar: Avatar) {
        _state.update { it.copy(choosenAvatar = avatar) }
    }

    fun onConfirmCreateProfile() {
        if (_state.value.username.isEmpty()) {
            showErrorMessage()
            return
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
