package com.example.quizler.ui.screen.quiz

interface IQuizResultStateGenerator {
    fun initSession(questionCount: Int)
    fun answered(isCorrect: Boolean)
    fun getResultInfo(): ResultInfo
    fun getSessionData(): QuizSessionData
    fun terminateCurrentSession()
}
