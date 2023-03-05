package com.example.quizler.ui.screen.newquestion

class NewQuestionValidator {
    fun validate(state: CreateNewQuestionScreenState): Boolean {
        return state.question.text.isEmpty() || state.answers.any { it.text.isEmpty() }
    }
}
