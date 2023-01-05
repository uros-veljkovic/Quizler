package com.example.quizler.ui.screen.score

import androidx.compose.runtime.Stable
import com.example.quizler.ui.components.InfoBannerData
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.ui.model.Score
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
data class ScoreScreenState(
    val chosenMode: DropdownItem.Content? = null,
    val isDropdownExpanded: Boolean = false,
    val modes: List<DropdownItem> = emptyList(),
    val scores: List<Score> = emptyList(),
    val isLoading: Boolean = false,
    val hasNetwork: Boolean = true,
    val infoBannerData: InfoBannerData? = null,
) {
    fun copyWithChosenMode(item: DropdownItem.Content, scores: List<Score>): ScoreScreenState {
        val filteredScores = scores.filter { it.mode == item.itemId }
        return copy(chosenMode = item, scores = filteredScores)
    }

    fun copyWithModes(list: List<DropdownItem>): ScoreScreenState {
        if (list.isEmpty()) return this
        val newCopy = list.firstOrNull { item -> item is DropdownItem.Content }?.let {
            copyWithChosenMode(it as DropdownItem.Content, scores)
        } ?: this

        return newCopy.copy(modes = list, infoBannerData = null)
    }

    fun copyWithScores(scores: List<Score>): ScoreScreenState {
        return copy(scores = getFilteredScores(scores), isLoading = false)
    }

    private fun getFilteredScores(list: List<Score>): List<Score> {
        return if (chosenMode != null) list.filter { it.mode == chosenMode.itemId } else list
    }

    fun copyWithDropdownExpandedStateChange(isExpanded: Boolean): ScoreScreenState {
        return copy(isDropdownExpanded = isExpanded)
    }

    fun copyWithLoading(isLoading: Boolean): ScoreScreenState {
        return copy(isLoading = isLoading)
    }

    fun copyWithError() = copy(
        infoBannerData = if (hasNetwork) InfoBannerData.ErrorLoadingContent else InfoBannerData.NoNetwork,
        isLoading = false
    )

    fun copyWithNoError(): ScoreScreenState {
        return copy(infoBannerData = null, isLoading = false)
    }

    fun copyHasConnection(hasConnection: Boolean): ScoreScreenState {
        return copy(hasNetwork = hasConnection)
    }
}
