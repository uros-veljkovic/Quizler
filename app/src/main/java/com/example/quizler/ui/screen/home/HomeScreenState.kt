package com.example.quizler.ui.screen.home

import androidx.compose.runtime.Stable
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.ui.screen.info.InfoScreenVariants
import javax.annotation.concurrent.Immutable

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

    fun copyWithLoading(isLoading: Boolean): HomeScreenState {
        return copy(isLoading = isLoading)
    }

    fun copyHasError(hasError: Boolean): HomeScreenState {
        return copy(infoScreenVariants = if (hasError) InfoScreenVariants.ErrorLoadingContent else null)
    }
}
