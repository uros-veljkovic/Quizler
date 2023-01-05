package com.example.quizler.domain.model

data class Answer(
    val id: String,
    val text: String,
    val type: AnswerType,
    val isCorrect: Boolean = false,
    val isChosen: Boolean = false
)
