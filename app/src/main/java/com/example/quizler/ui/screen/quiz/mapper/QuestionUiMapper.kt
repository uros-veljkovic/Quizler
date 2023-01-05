package com.example.quizler.ui.screen.quiz.mapper

import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.domain.model.Question
import com.example.quizler.util.mapper.DataMapper

class QuestionUiMapper : DataMapper<QuestionEntity, Question> {
    override fun map(input: QuestionEntity): Question {
        return Question(
            id = input.id,
            text = input.text,
            categoryId = input.categoryId,
        )
    }
}
