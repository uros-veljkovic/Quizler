package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.AnswerEntity
import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class QuestionWithAnswersDtoMapper @Inject constructor(
    private val questionMapper: DataMapper<QuestionDto, QuestionEntity>,
    private val answerMapper: DataMapper<QuestionDto, List<AnswerEntity>>
) : DataMapper<QuestionDto, QuestionWithAnswersEntity> {

    override fun map(input: QuestionDto): QuestionWithAnswersEntity {
        return QuestionWithAnswersEntity(
            question = questionMapper.map(input), answers = answerMapper.map(input)
        )
    }
}
