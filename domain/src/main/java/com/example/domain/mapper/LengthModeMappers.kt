package com.example.domain.mapper

import com.example.data.local.entity.LengthModeEntity
import com.example.data.remote.dto.LengthModesDto
import com.example.domain.model.mode.LengthMode
import com.example.util.mapper.DataMapper

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

class LengthModeMapper : DataMapper<LengthModeEntity, LengthMode> {
    override fun map(input: LengthModeEntity): LengthMode {
        return LengthMode(
            id = input.id,
            name = input.name,
            numberOfQuestions = input.numberOfQuestions,
            numberOfHints = input.numberOfHints,
            timePerQuestion = input.timePerQuestion
        )
    }
}
