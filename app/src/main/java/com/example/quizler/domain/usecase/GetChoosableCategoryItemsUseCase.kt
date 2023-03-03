package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChoosableCategoryItemsUseCase @Inject constructor(
    private val getModesCategoryUseCase: GetModesCategoryUseCase,
    private val mapper: DataMapper<BaseQuizModeEntity, ChosableItem.Content>,
) {
    operator fun invoke() = getModesCategoryUseCase().map { mapper.map(it) }
}