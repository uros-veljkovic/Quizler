package com.example.quizler.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.quizler.R

sealed class InfoBannerData(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val color: Color,
    @StringRes val actionButtonText: Int? = null,
) {
    object NoNetwork : InfoBannerData(
        icon = R.drawable.ic_no_wifi,
        title = R.string.no_internet_connection,
        description = R.string.no_internet_connection_description,
        color = Color(0xffef9a9a),
        actionButtonText = R.string.try_again
    )

    object ErrorLoadingContent : InfoBannerData(
        icon = R.drawable.ic_no_content,
        title = R.string.error_unknow,
        description = R.string.error_unknow_description,
        color = Color(0xffffcc80),
    )

    object Loading : InfoBannerData(
        icon = R.drawable.ic_sand_clock,
        title = R.string.loading,
        color = Color(0xff90caf9)
    )

    object ReportQuestion : InfoBannerData(
        icon = R.drawable.ic_magnifying_glass,
        title = R.string.report_question_title,
        description = R.string.report_question_description,
        color = Color(0xffCBEDFD)
    )
}
