package com.example.quizler.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Chip(
    val title: String,
    @DrawableRes val iconResId: Int,
    val background: Color,
)
