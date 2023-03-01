package com.example.quizler.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.quizler.R

sealed class ChosableItem {
    data class Title(
        @StringRes val value: Int
    ) : ChosableItem()

    data class Content(
        val itemId: String,
        @DrawableRes val icon: Int,
        val text: String
    ) : ChosableItem()

    companion object {
        fun dummyContent(): ChosableItem.Content = ChosableItem.Content("", R.drawable.app_logo, "")
    }
}
