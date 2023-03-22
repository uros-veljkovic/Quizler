package com.example.domain.mapper

import com.example.data.local.entity.DifficultyModeEntity
import com.example.data.remote.dto.DifficultyModesDto
import com.example.domain.model.mode.DifficultyMode
import com.example.util.mapper.DataMapper

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

class DifficultyModeMapper : DataMapper<DifficultyModeEntity, DifficultyMode> {
    override fun map(input: DifficultyModeEntity): DifficultyMode {
        return DifficultyMode(
            id = input.id,
            name = input.name,
            numberOfQuestions = input.numberOfQuestions,
            numberOfHints = input.numberOfHints,
            timePerQuestion = input.timePerQuestion
        )
    }
}
