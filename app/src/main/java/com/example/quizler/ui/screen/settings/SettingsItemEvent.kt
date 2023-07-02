package com.example.quizler.ui.screen.settings

sealed class SettingsItemEvent {
    object Personalize : SettingsItemEvent()
    object Share : SettingsItemEvent()
    object RateApp : SettingsItemEvent()
    object PrivacePolicy : SettingsItemEvent()
    data class WriteEmail(val toEmail: String) : SettingsItemEvent()
}
