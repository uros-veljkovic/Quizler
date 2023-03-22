package com.example.quizler.ui.screen.newquestion

import com.example.domain.usecase.IGetModesCategoryUseCase
import com.example.quizler.ui.mapper.QuizModeDropdownItemMapper
import kotlinx.coroutines.flow.map

class ChoosableCategoryItemsProvider(
    private val getModesCategoryUseCase: IGetModesCategoryUseCase,
    private val mapper: QuizModeDropdownItemMapper,
) {
    operator fun invoke() = getModesCategoryUseCase().map { mapper.map(it) }
}
