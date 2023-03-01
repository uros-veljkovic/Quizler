package com.example.quizler.domain.usecase

import com.example.quizler.R
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChosableModeItemsUseCase @Inject constructor(
    private val getModesLengthUseCase: GetModesLengthUseCase,
    private val getModesDifficultyUseCase: GetModesDifficultyUseCase,
    private val getModesCategoryUseCase: GetModesCategoryUseCase,
    private val mapper: DataMapper<BaseQuizModeEntity, ChosableItem.Content>,
) {

    operator fun invoke() = combine(
        getModesCategoryUseCase(),
        getModesLengthUseCase(),
        getModesDifficultyUseCase()
    ) { categories, lengths, difficulties ->
        getFlattenedListOfDropdownItemsWithTitles(categories, difficulties, lengths)
    }

    private fun getFlattenedListOfDropdownItemsWithTitles(
        categories: List<CategoryModeEntity>,
        difficulties: List<DifficultyModeEntity>,
        lengths: List<LengthModeEntity>
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

class GetChoosableCategoryItemsUseCase @Inject constructor(
    private val getModesCategoryUseCase: GetModesCategoryUseCase,
    private val mapper: DataMapper<BaseQuizModeEntity, ChosableItem.Content>,
) {
    operator fun invoke() = getModesCategoryUseCase().map { mapper.map(it) }
}
