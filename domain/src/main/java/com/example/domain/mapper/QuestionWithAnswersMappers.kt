package com.example.domain.mapper

import com.example.data.local.entity.QuestionWithAnswersEntity
import com.example.data.remote.dto.QuestionDto
import com.example.domain.model.AnswerEntityBundle
import com.example.domain.model.AnswerType
import com.example.domain.model.Difficulty
import com.example.domain.model.QuestionWithAnswers
import com.example.util.mapper.DataMapper

class QuestionWithAnswersDtoMapper(
    private val questionMapper: QuestionDtoMappers,
    private val answerMapper: AnswerDtoMapper
) : DataMapper<QuestionDto, QuestionWithAnswersEntity> {

    override fun map(input: QuestionDto): QuestionWithAnswersEntity {
        return QuestionWithAnswersEntity(
            question = questionMapper.map(input), answers = answerMapper.map(input)
        )
    }
}

class QuestionWithAnswersUiMapper(
    private val questionMapper: QuestionDomainMapper,
    private val answerMapper: AnswerDomainMapper
) : DataMapper<QuestionWithAnswersEntity, QuestionWithAnswers> {
    override fun map(input: QuestionWithAnswersEntity): QuestionWithAnswers {
        val answerA = input.answers.getOrNull(0)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.A))
        }
        val answerB = input.answers.getOrNull(1)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.B))
        }
        val answerC = input.answers.getOrNull(2)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.C))
        }
        val answerD = input.answers.getOrNull(3)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.D))
        }
        return QuestionWithAnswers(
            time = 20,
            question = questionMapper.map(input.question),
            answers = listOfNotNull(answerA, answerB, answerC, answerD),
            difficulty = Difficulty.valueOf(input.question.difficulty)
        )
    }
}
