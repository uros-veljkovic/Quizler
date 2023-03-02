package com.example.quizler.data.remote.dto

data class CreateNewQuestionAnswerDto(
    val text: String,
    val isCorrect: Boolean,
)