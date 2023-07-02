package com.example.quizler.ui.screen.settings

import com.example.quizler.R
import com.example.quizler.model.InfoBannerData

data class SettingsScreenState(
    val infoBannerData: InfoBannerData? = null
) {
    val settingsItems: List<SettingsItem> = listOf(
        SettingsItem.Header(titleStringRes = R.string.settings_title_personalize),
        SettingsItem.Button(
            titleStringRes = R.string.setttings_item_personalize,
            frontIconRes = R.drawable.ic_pallete,
            event = SettingsItemEvent.Personalize
        ),
        SettingsItem.Header(titleStringRes = R.string.settings_title_support_kvizler),
        SettingsItem.ButtonGroup(
            listOf(
                SettingsItem.Button(
                    titleStringRes = R.string.setttings_item_share,
                    frontIconRes = R.drawable.ic_share,
                    event = SettingsItemEvent.Share
                ),
                SettingsItem.Button(
                    titleStringRes = R.string.settings_item_rate_app,
                    frontIconRes = R.drawable.ic_star,
                    event = SettingsItemEvent.RateApp
                )
            )
        ),
        SettingsItem.Header(titleStringRes = R.string.settings_title_other),
        SettingsItem.ButtonGroup(
            listOf(
                SettingsItem.Button(
                    titleStringRes = R.string.settings_item_write_suggestion,
                    frontIconRes = R.drawable.ic_outgoing_mail,
                    event = SettingsItemEvent.WriteEmail("urosveljkovic@yahoo.com")
                ),
                SettingsItem.Button(
                    titleStringRes = R.string.settings_item_privacy_policy,
                    frontIconRes = R.drawable.ic_policy,
                    event = SettingsItemEvent.PrivacePolicy
                )
            )
        )
    )
}
