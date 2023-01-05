package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.AnswerEntity
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.util.mapper.DataMapper

class AnswerDtoMapper : DataMapper<QuestionDto, List<AnswerEntity>> {
    override fun map(input: QuestionDto): List<AnswerEntity> {
        return input.answers.map { answer ->
            AnswerEntity(
                questionId = input.id,
                answerId = answer.id,
                text = answer.text,
                isCorrect = answer.isCorrect
            )
        }
    }
}
