package com.example.quizler.ui.screen.settings

sealed class SettingsItem {
    data class Header(val titleStringRes: Int) : SettingsItem()
    data class Button(val titleStringRes: Int, val frontIconRes: Int, val onClick: () -> Unit) : SettingsItem()
    data class ButtonGroup(val buttons: List<Button>) : SettingsItem()
}