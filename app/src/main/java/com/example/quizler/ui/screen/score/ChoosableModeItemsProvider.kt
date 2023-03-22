package com.example.quizler.ui.screen.score

import com.example.domain.model.mode.CategoryMode
import com.example.domain.model.mode.DifficultyMode
import com.example.domain.model.mode.LengthMode
import com.example.domain.usecase.IGetModesCategoryUseCase
import com.example.domain.usecase.IGetModesDifficultyUseCase
import com.example.domain.usecase.IGetModesLengthUseCase
import com.example.quizler.R
import com.example.quizler.model.ChosableItem
import com.example.quizler.ui.mapper.QuizModeDropdownItemMapper
import kotlinx.coroutines.flow.combine

class ChoosableModeItemsProvider(
    private val getModesLengthUseCase: IGetModesLengthUseCase,
    private val getModesDifficultyUseCase: IGetModesDifficultyUseCase,
    private val getModesCategoryUseCase: IGetModesCategoryUseCase,
    private val mapper: QuizModeDropdownItemMapper,
) {

    operator fun invoke() = combine(
        getModesCategoryUseCase(), getModesLengthUseCase(), getModesDifficultyUseCase()
    ) { categories, lengths, difficulties ->
        getFlattenedListOfDropdownItemsWithTitles(categories, difficulties, lengths)
    }

    private fun getFlattenedListOfDropdownItemsWithTitles(
        categories: List<CategoryMode>,
        difficulties: List<DifficultyMode>,
        lengths: List<LengthMode>
    ): List<ChosableItem> {

        val categoryChosableItems = mapper.map(categories) as List<ChosableItem>
        val difficultyChosableItems = mapper.map(difficulties) as List<ChosableItem>
        val lengthChosableItems = mapper.map(lengths) as List<ChosableItem>

        return listOf(
            categoryChosableItems.toMutableList().apply {
                add(0, ChosableItem.Title(R.string.tab_category))
            },
            difficultyChosableItems.toMutableList().apply {
                add(0, ChosableItem.Title(R.string.tab_difficulty))
            },
            lengthChosableItems.toMutableList().apply {
                add(0, ChosableItem.Title(R.string.tab_length))
            }
        ).flatten()
    }
}
