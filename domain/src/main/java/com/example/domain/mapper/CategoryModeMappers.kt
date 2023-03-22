package com.example.domain.mapper

import com.example.data.local.entity.CategoryModeEntity
import com.example.data.remote.dto.CategoryModesDto
import com.example.domain.model.mode.CategoryMode
import com.example.util.mapper.DataMapper

class CategoryModeMapper : DataMapper<CategoryModeEntity, CategoryMode> {
    override fun map(input: CategoryModeEntity): CategoryMode {
        return CategoryMode(
            id = input.id,
            name = input.name,
            numberOfQuestions = input.numberOfQuestions,
            numberOfHints = input.numberOfHints,
            timePerQuestion = input.timePerQuestion
        )
    }
}

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
