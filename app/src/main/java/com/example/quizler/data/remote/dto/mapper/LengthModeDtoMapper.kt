package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.data.remote.dto.LengthModesDto
import com.example.quizler.util.mapper.DataMapper

class LengthModeDtoMapper : DataMapper<LengthModesDto, List<LengthModeEntity>> {
    override fun map(input: LengthModesDto): List<LengthModeEntity> {
        return input.submodes.map {
            LengthModeEntity(
                id = it.id,
                name = it.name,
                numberOfQuestions = it.numberOfQuestions,
                numberOfHints = it.numberOfHints,
                timePerQuestion = it.timePerQuestion
            )
        }
    }
}
