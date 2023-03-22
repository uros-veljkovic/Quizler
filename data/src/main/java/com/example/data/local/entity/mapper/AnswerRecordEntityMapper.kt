package com.example.data.local.entity.mapper

import com.example.data.local.entity.AnswerRecordEntity
import com.example.data.remote.dto.AnswerRecordDto
import com.example.util.mapper.DataMapper

class AnswerRecordEntityMapper : DataMapper<AnswerRecordEntity, AnswerRecordDto> {

    override fun map(input: AnswerRecordEntity): AnswerRecordDto {
        return AnswerRecordDto(input.questionId, isCorrect = input.isAnsweredCorrectly)
    }
}
