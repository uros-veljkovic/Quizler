package com.example.quizler.ui.screen.newquestion

import com.example.domain.model.AnswerType

data class AnswerFieldState(
    val type: AnswerType,
    val text: String = "",
    val errorMessage: String? = null
)
