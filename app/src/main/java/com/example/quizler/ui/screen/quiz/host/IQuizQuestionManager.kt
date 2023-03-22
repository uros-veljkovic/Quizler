package com.example.quizler.ui.screen.quiz.host

import com.example.domain.model.QuestionWithAnswers

interface IQuizQuestionManager {
    suspend fun getQuestions(
        modeId: String
    ): List<QuestionWithAnswers>

    fun getTime(modeId: String): Int
}
