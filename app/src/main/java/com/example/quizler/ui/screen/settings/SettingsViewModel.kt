package com.example.quizler.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SettingsRow {
    data class Title(val titleStringRes: Int) : SettingsRow()
    data class Item(val titleStringRes: Int, val frontIconRes: Int, val onClick: () -> Unit) : SettingsRow()
    data class ItemGroup(val items: List<Item>) : SettingsRow()
}

sealed class SettingsItemEvent {
    object Personalize : SettingsItemEvent()
    object Share : SettingsItemEvent()
    object RateApp : SettingsItemEvent()
    object PrivacePolicy : SettingsItemEvent()
    data class WriteEmail(val toEmail: String) : SettingsItemEvent()
}

class SettingsViewModel : ViewModel() {

    private val _onSettingsItemClickEvent = MutableSharedFlow<SettingsItemEvent>()
    val onSettingsItemClickEvent = _onSettingsItemClickEvent.asSharedFlow()

    private val _settingsRow: MutableStateFlow<List<SettingsRow>> = MutableStateFlow(
        listOf(
            SettingsRow.Title(titleStringRes = R.string.settings_title_personalize),
            SettingsRow.Item(
                titleStringRes = R.string.setttings_item_personalize,
                frontIconRes = R.drawable.ic_pallete,
                onClick = {
                    emitEvent(SettingsItemEvent.Personalize)
                }
            ),
            SettingsRow.Title(titleStringRes = R.string.settings_title_support_kvizler),
            SettingsRow.ItemGroup(
                listOf(
                    SettingsRow.Item(
                        titleStringRes = R.string.setttings_item_share,
                        frontIconRes = R.drawable.ic_share,
                        onClick = {
                            emitEvent(SettingsItemEvent.Share)
                        }
                    ),
                    SettingsRow.Item(
                        titleStringRes = R.string.settings_item_rate_app,
                        frontIconRes = R.drawable.ic_star,
                        onClick = {
                            emitEvent(SettingsItemEvent.RateApp)
                        }
                    )
                )
            ),
            SettingsRow.Title(titleStringRes = R.string.settings_title_other),
            SettingsRow.ItemGroup(
                listOf(
                    SettingsRow.Item(
                        titleStringRes = R.string.settings_item_send_email_to_uros,
                        frontIconRes = R.drawable.ic_outgoing_mail,
                        onClick = {
                            emitEvent(SettingsItemEvent.WriteEmail("urosveljkovic@yahoo.com"))
                        }
                    ),
                    SettingsRow.Item(
                        titleStringRes = R.string.settings_item_send_email_to_petar,
                        frontIconRes = R.drawable.ic_outgoing_mail,
                        onClick = {
                            emitEvent(SettingsItemEvent.WriteEmail("applesakota@gmail.com"))
                        }
                    ),
                    SettingsRow.Item(
                        titleStringRes = R.string.settings_item_privacy_policy,
                        frontIconRes = R.drawable.ic_policy,
                        onClick = {
                            emitEvent(SettingsItemEvent.Personalize)
                        }
                    )
                )
            )
        )
    )
    val settingsRow = _settingsRow.asStateFlow()

    private fun emitEvent(event: SettingsItemEvent) {
        viewModelScope.launch {
            _onSettingsItemClickEvent.emit(event)
        }
    }
}
