package com.example.quizler.data.local.entity.mapper

import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.util.mapper.DataMapper

class AnswerRecordEntityMapper : DataMapper<AnswerRecordEntity, AnswerRecordDto> {

    override fun map(input: AnswerRecordEntity): AnswerRecordDto {
        return AnswerRecordDto(input.questionId, isCorrect = input.isAnsweredCorrectly)
    }
}
