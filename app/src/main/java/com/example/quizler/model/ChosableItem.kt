package com.example.quizler.model

import androidx.annotation.StringRes

sealed interface ChosableItem {
    data class Title(
        @StringRes val value: Int
    ) : ChosableItem

    data class Content(
        val itemId: String,
        val icon: Int,
        val text: String
    ) : ChosableItem
}
