package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.remote.dto.CategoryModesDto
import com.example.quizler.util.mapper.DataMapper

class CategoryModeDtoMapper : DataMapper<CategoryModesDto, List<CategoryModeEntity>> {
    override fun map(input: CategoryModesDto): List<CategoryModeEntity> {
        return input.submodes.map {
            CategoryModeEntity(
                id = it.id,
                name = it.name,
                numberOfQuestions = it.numberOfQuestions,
                numberOfHints = it.numberOfHints,
                timePerQuestion = it.timePerQuestion
            )
        }
    }
}
