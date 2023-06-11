package com.example.quizler.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.quizler.R

open class InfoBannerData(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val color: Color,
    @StringRes val actionButtonText: Int? = null,
) {

    object SignInFailed : InfoBannerData(
        icon = R.mipmap.ic_sign_in_failed_foreground,
        title = R.string.sign_in_failed_title,
        description = R.string.sign_in_failed_desc,
        color = Color(0xffef9a9a)
    )

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

    class DataRefreshed(title: Int = R.string.data_refresh_title) : InfoBannerData(
        icon = R.drawable.ic_refresh,
        title = title,
        color = Color(0xffaee4da)
    )

    object InvalidQuestionFields : InfoBannerData(
        icon = R.mipmap.ic_error_foreground,
        title = R.string.banner_error_creating_question_title,
        description = R.string.banner_error_creating_question_description,
        color = Color(0xffef9a9a)
    )

    object CreateProfileError : InfoBannerData(
        icon = R.mipmap.ic_error_foreground,
        title = R.string.banner_error_create_profile_title,
        description = R.string.banner_error_create_profile_description,
        color = Color(0xffef9a9a)
    )

    object SuccessfullyCreatedNewQuestion : InfoBannerData(
        icon = R.drawable.ic_in_love,
        title = R.string.wohoo,
        description = R.string.wohoo_description,
        color = Color(0xffaee4da)
    )
}
