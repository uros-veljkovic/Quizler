package com.example.quizler.ui.screen.score

import androidx.compose.runtime.Stable
import com.example.quizler.model.ChosableItem
import com.example.quizler.model.InfoBannerData
import com.example.quizler.model.Score

@Stable
data class ScoreScreenState(
    private val chosenMode: ChosableItem.Content? = null,
    val isDropdownExpanded: Boolean = false,
    val modes: List<ChosableItem> = emptyList(),
    private val scores: List<Score> = emptyList(),
    val isLoading: Boolean = false,
    val isDataRefreshed: Boolean = false,
    val infoBannerData: InfoBannerData? = null,
) {
    fun getFilteredScores(): List<Score> {
        return if (getChosenMode() != null) scores.filter { it.mode == getChosenMode()?.itemId } else scores
    }

    fun getChosenMode(): ChosableItem.Content? {
        return chosenMode ?: modes.firstOrNull { item -> item is ChosableItem.Content } as? ChosableItem.Content
    }
}
