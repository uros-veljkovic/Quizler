package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.remote.dto.DifficultyModesDto
import com.example.quizler.util.mapper.DataMapper

class DifficultyModeDtoMapper : DataMapper<DifficultyModesDto, List<DifficultyModeEntity>> {
    override fun map(input: DifficultyModesDto): List<DifficultyModeEntity> {
        return input.submodes.map {
            DifficultyModeEntity(
                id = it.id,
                name = it.name,
                numberOfQuestions = it.numberOfQuestions,
                numberOfHints = it.numberOfHints,
                timePerQuestion = it.timePerQuestion
            )
        }
    }
}
