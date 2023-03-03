package com.example.quizler.ui.screen.newquestion

data class QuestionFieldState(
    val text: String = "",
    // TODO: Use when error message per text field suits design
    val errorMessage: String? = null
)
