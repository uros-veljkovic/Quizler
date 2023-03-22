package com.example.quizler.ui.screen.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.quizler.components.info.InfoScreenVariants
import com.example.quizler.model.QuizMode

@Immutable
@Stable
data class HomeScreenState(
    val isLoading: Boolean = false,
    val infoScreenVariants: InfoScreenVariants? = null,
    val categories: List<QuizMode> = emptyList(),
    val length: List<QuizMode> = emptyList(),
    val difficulties: List<QuizMode> = emptyList()
) {
    fun copyWithCategoryModes(list: List<QuizMode>): HomeScreenState {
        return copy(categories = list)
    }

    fun copyWithDifficultyModes(list: List<QuizMode>): HomeScreenState {
        return copy(difficulties = list)
    }

    fun copyWithLengthModes(list: List<QuizMode>): HomeScreenState {
        return copy(length = list)
    }

    fun copyHasError(hasError: Boolean): HomeScreenState {
        return copy(infoScreenVariants = if (hasError) InfoScreenVariants.ErrorLoadingContent else null)
    }
}
