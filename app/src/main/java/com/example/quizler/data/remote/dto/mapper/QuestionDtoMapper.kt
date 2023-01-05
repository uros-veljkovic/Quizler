package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.domain.model.Difficulty
import com.example.quizler.util.mapper.DataMapper

class QuestionDtoMapper : DataMapper<QuestionDto, QuestionEntity> {
    override fun map(input: QuestionDto): QuestionEntity {
        return QuestionEntity(
            id = input.id,
            difficulty = Difficulty.generate(
                input.countAnsweredCorrect,
                input.countAnsweredWrong
            ),
            text = input.text,
            categoryId = input.categoryId
        )
    }
}
