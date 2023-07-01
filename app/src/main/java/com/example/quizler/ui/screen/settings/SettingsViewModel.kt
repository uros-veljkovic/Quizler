package com.example.quizler.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _onSettingsItemClickEvent = MutableSharedFlow<SettingsItemEvent>()
    val onSettingsItemClickEvent = _onSettingsItemClickEvent.asSharedFlow()

    private val _settingsItems: MutableStateFlow<List<SettingsItem>> = MutableStateFlow(
        listOf(
            SettingsItem.Header(titleStringRes = R.string.settings_title_personalize),
            SettingsItem.Button(
                titleStringRes = R.string.setttings_item_personalize,
                frontIconRes = R.drawable.ic_pallete,
                onClick = {
                    emitEvent(SettingsItemEvent.Personalize)
                }
            ),
            SettingsItem.Header(titleStringRes = R.string.settings_title_support_kvizler),
            SettingsItem.ButtonGroup(
                listOf(
                    SettingsItem.Button(
                        titleStringRes = R.string.setttings_item_share,
                        frontIconRes = R.drawable.ic_share,
                        onClick = {
                            emitEvent(SettingsItemEvent.Share)
                        }
                    ),
                    SettingsItem.Button(
                        titleStringRes = R.string.settings_item_rate_app,
                        frontIconRes = R.drawable.ic_star,
                        onClick = {
                            emitEvent(SettingsItemEvent.RateApp)
                        }
                    )
                )
            ),
            SettingsItem.Header(titleStringRes = R.string.settings_title_other),
            SettingsItem.ButtonGroup(
                listOf(
                    SettingsItem.Button(
                        titleStringRes = R.string.settings_item_send_email_to_uros,
                        frontIconRes = R.drawable.ic_outgoing_mail,
                        onClick = {
                            emitEvent(SettingsItemEvent.WriteEmail("urosveljkovic@yahoo.com"))
                        }
                    ),
                    SettingsItem.Button(
                        titleStringRes = R.string.settings_item_send_email_to_petar,
                        frontIconRes = R.drawable.ic_outgoing_mail,
                        onClick = {
                            emitEvent(SettingsItemEvent.WriteEmail("applesakota@gmail.com"))
                        }
                    ),
                    SettingsItem.Button(
                        titleStringRes = R.string.settings_item_privacy_policy,
                        frontIconRes = R.drawable.ic_policy,
                        onClick = {
                            emitEvent(SettingsItemEvent.PrivacePolicy)
                        }
                    )
                )
            )
        )
    )
    val settingsItems = _settingsItems.asStateFlow()

    private fun emitEvent(event: SettingsItemEvent) {
        viewModelScope.launch {
            _onSettingsItemClickEvent.emit(event)
        }
    }
}
