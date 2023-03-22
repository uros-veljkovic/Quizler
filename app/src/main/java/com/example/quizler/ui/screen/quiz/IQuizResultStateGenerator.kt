package com.example.quizler.ui.screen.quiz

import com.example.quizler.model.ResultInfo

interface IQuizResultStateGenerator {
    fun initSession(questionCount: Int)
    fun answered(isCorrect: Boolean)
    fun getResultInfo(): ResultInfo
    fun getSessionData(): QuizSessionData
    fun terminateCurrentSession()
}
