package com.example.domain.mapper

import com.example.data.local.entity.AnswerRecordEntity
import com.example.data.remote.dto.AnswerRecordDto
import com.example.domain.model.AnswerRecord
import com.example.util.mapper.DataMapper

class AnswerRecordDomainMapper : DataMapper<AnswerRecord, AnswerRecordEntity> {
    override fun map(input: AnswerRecord): AnswerRecordEntity {
        return AnswerRecordEntity(
            questionId = input.questionId,
            isAnsweredCorrectly = input.isCorrect
        )
    }
}

class AnswerRecordDtoMapper : DataMapper<AnswerRecord, AnswerRecordDto> {
    override fun map(input: AnswerRecord): AnswerRecordDto {
        return AnswerRecordDto(
            questionId = input.questionId,
            isCorrect = input.isCorrect
        )
    }
}
