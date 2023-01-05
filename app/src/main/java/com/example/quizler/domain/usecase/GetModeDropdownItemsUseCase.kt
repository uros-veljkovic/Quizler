package com.example.quizler.domain.usecase

import com.example.quizler.R
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetModeDropdownItemsUseCase @Inject constructor(
    private val getModesLengthUseCase: GetModesLengthUseCase,
    private val getModesDifficultyUseCase: GetModesDifficultyUseCase,
    private val getModesCategoryUseCase: GetModesCategoryUseCase,
    private val mapper: DataMapper<BaseQuizModeEntity, DropdownItem.Content>,
) {

    operator fun invoke() = combine(
        getModesCategoryUseCase(), getModesLengthUseCase(), getModesDifficultyUseCase()
    ) { categories, lengths, difficulties ->
        getFlattenedListOfDropdownItemsWithTitles(categories, difficulties, lengths)
    }

    private fun getFlattenedListOfDropdownItemsWithTitles(
        categories: List<CategoryModeEntity>,
        difficulties: List<DifficultyModeEntity>,
        lengths: List<LengthModeEntity>
    ): List<DropdownItem> {
        val categoryDropdownItems = mapper.map(categories) as List<DropdownItem>
        val difficultyDropdownItems =
            mapper.map(difficulties) as List<DropdownItem>
        val lengthDropdownItems = mapper.map(lengths) as List<DropdownItem>
        return listOf(
            categoryDropdownItems.toMutableList().apply {
                add(0, DropdownItem.Title(R.string.tab_category))
            },
            difficultyDropdownItems.toMutableList().apply {
                add(0, DropdownItem.Title(R.string.tab_difficulty))
            },
            lengthDropdownItems.toMutableList().apply {
                add(0, DropdownItem.Title(R.string.tab_length))
            }
        ).flatten()
    }
}
