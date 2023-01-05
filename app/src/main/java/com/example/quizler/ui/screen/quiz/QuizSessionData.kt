package com.example.quizler.ui.screen.quiz

data class QuizSessionData(
    val totalNumberOfQuestions: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalPoints: Int = 0,
    val consecutiveCorrectAnswers: Int = 0,
    val previousQuestionValue: Int = 0,
    val currentQuestionValue: Int = 1,
    val maxConsecutiveCorrectAnswers: Int = 0,
) {
    fun copyOnQuestionAnsweredCorrect(): QuizSessionData {
        val newConsecutiveCorrectAnswers = consecutiveCorrectAnswers + 1
        val holder = previousQuestionValue
        val newMaxConsecutiveCorrectAnswers = if (newConsecutiveCorrectAnswers > maxConsecutiveCorrectAnswers) {
            newConsecutiveCorrectAnswers
        } else {
            maxConsecutiveCorrectAnswers
        }
        return copy(
            totalCorrectAnswers = totalCorrectAnswers + 1,
            totalPoints = totalPoints + currentQuestionValue,
            consecutiveCorrectAnswers = newConsecutiveCorrectAnswers,
            previousQuestionValue = currentQuestionValue,
            currentQuestionValue = currentQuestionValue + holder,
            maxConsecutiveCorrectAnswers = newMaxConsecutiveCorrectAnswers
        )
    }

    fun copyOnQuestionAnsweredWrong(): QuizSessionData {
        return copy(
            consecutiveCorrectAnswers = 0,
            previousQuestionValue = 0,
            currentQuestionValue = 1
        )
    }
}
