package com.example.quizler.ui.screen.quiz.host

import com.example.quizler.ui.screen.quiz.QuestionBundle

interface IQuizQuestionManager {
    suspend fun getQuestions(
        modeId: String
    ): List<QuestionBundle>

    fun getTime(modeId: String): Int
}
