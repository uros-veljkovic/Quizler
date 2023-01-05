package com.example.quizler.ui.screen.quiz.mapper

import com.example.quizler.domain.model.Answer
import com.example.quizler.ui.screen.quiz.bundle.AnswerEntityBundle
import com.example.quizler.util.mapper.DataMapper

class AnswerUiMapper : DataMapper<AnswerEntityBundle, Answer> {
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
