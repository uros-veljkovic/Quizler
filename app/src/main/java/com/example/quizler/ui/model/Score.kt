package com.example.quizler.ui.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class Score(
    val id: String,
    val username: String,
    val mode: String,
    val ranking: Int,
    val score: Int
) {
    @Composable
    fun getRankingColor(): Color {
        return when (ranking) {
            1 -> Color(0xffFFD700)
            2 -> Color(0xffC0C0C0)
            3 -> Color(0xffCD7F32)
            4 -> Color(0xff4B5966)
            else -> MaterialTheme.colorScheme.secondary
        }
    }
}
