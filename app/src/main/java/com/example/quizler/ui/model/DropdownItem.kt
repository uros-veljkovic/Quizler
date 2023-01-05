package com.example.quizler.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class DropdownItem {
    data class Title(
        @StringRes val value: Int
    ) : DropdownItem()

    data class Content(
        val itemId: String,
        @DrawableRes val icon: Int,
        val text: String
    ) : DropdownItem()
}
