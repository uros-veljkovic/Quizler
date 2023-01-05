package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.util.mapper.DataMapper

class AnswerRecordDtoMapper : DataMapper<AnswerRecordDto, AnswerRecordEntity> {
    override fun map(input: AnswerRecordDto): AnswerRecordEntity {
        return AnswerRecordEntity(
            questionId = input.questionId,
            isAnsweredCorrectly = input.isCorrect
        )
    }
}
