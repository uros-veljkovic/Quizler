package com.example.quizler.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class ResultInfo(
    @DrawableRes val icon: Int,
    val title: String,
    val description: String,
)
