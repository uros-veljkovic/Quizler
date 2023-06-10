package com.example.quizler.ui.screen.onboarding.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmptyViewModel : ViewModel() {

    private val _nextScreen = MutableStateFlow("")
    val nextScreen = _nextScreen.asStateFlow()

    init {
        gotoNextScreen()
    }

    private fun gotoNextScreen() {
        viewModelScope.launch {
            _nextScreen.emit(MainScreen.SignIn.route)
        }
    }
}
