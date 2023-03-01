package com.example.quizler.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.quizler.R

sealed class DropdownItem {
    data class Title(
        @StringRes val value: Int
    ) : DropdownItem()

    data class Content(
        val itemId: String,
        @DrawableRes val icon: Int,
        val text: String
    ) : DropdownItem()

    companion object {
        fun dummyContent(): DropdownItem.Content = DropdownItem.Content("", R.drawable.app_logo, "")
    }
}
