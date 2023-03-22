package com.example.domain.mapper

import com.example.data.local.entity.AnswerEntity
import com.example.data.remote.dto.QuestionDto
import com.example.domain.model.Answer
import com.example.domain.model.AnswerEntityBundle
import com.example.util.mapper.DataMapper

class AnswerDomainMapper : DataMapper<AnswerEntityBundle, Answer> {
    override fun map(input: AnswerEntityBundle): Answer {
        return Answer(
            id = input.answer.answerId,
            text = input.answer.text,
            type = input.type,
            isCorrect = input.answer.isCorrect,
            isChosen = false,
        )
    }
}

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
