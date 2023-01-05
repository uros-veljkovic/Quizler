package com.example.quizler.ui.screen.info

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.quizler.R

sealed class InfoScreenVariants(
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    @StringRes val actionButton: Int? = null,
) {
    object NoScore : InfoScreenVariants(
        iconResId = R.drawable.ic_competition,
        titleResId = R.string.no_score_info_screen_title,
        descriptionResId = R.string.no_score_info_screen_description
    )

    object ErrorLoadingContent : InfoScreenVariants(
        iconResId = R.drawable.ic_no_content,
        titleResId = R.string.no_content_info_screen_title,
        descriptionResId = R.string.no_content_info_screen_description
    )

    object NoNetwork : InfoScreenVariants(
        iconResId = R.drawable.ic_no_wifi,
        titleResId = R.string.no_internet_connection,
        descriptionResId = R.string.no_internet_connection_description
    )
}
