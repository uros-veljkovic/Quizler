package com.example.quizler.ui.screen.score

import androidx.compose.runtime.Stable
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.model.Score
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
data class ScoreScreenState(
    private val chosenMode: DropdownItem.Content? = null,
    val isDropdownExpanded: Boolean = false,
    val modes: List<DropdownItem> = emptyList(),
    private val scores: List<Score> = emptyList(),
    val isLoading: Boolean = false,
    val isDataRefreshed: Boolean = false,
    val hasNetwork: Boolean = true,
    val infoBannerData: InfoBannerData? = null,
) {
    fun getFilteredScores(): List<Score> {
        return if (getChosenMode() != null) scores.filter { it.mode == getChosenMode()?.itemId } else scores
    }

    fun getChosenMode(): DropdownItem.Content? {
        return chosenMode ?: modes.firstOrNull { item -> item is DropdownItem.Content } as? DropdownItem.Content
    }

    // TODO: Handle when network connection handled properly
    fun copyWithError(): ScoreScreenState {
        return copy(infoBannerData = InfoBannerData.NoNetwork, isLoading = false)
    }

    fun copyWithNoError(): ScoreScreenState {
        return copy(infoBannerData = null, isLoading = false)
    }
}
