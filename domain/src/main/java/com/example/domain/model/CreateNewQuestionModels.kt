package com.example.domain.model

data class CreateNewQuestionBundle(
    val question: String,
    val categoryId: String,
    val answers: List<CreateNewQuestionAnswer>
)

data class CreateNewQuestionAnswer(
    val text: String,
    val isCorrect: Boolean
)
