package com.example.quizler.data.local.entity

open class BaseQuizModeEntity(
    val id: String,
    val name: String,
    val numberOfHints: Int,
    val numberOfQuestions: Int,
    val timePerQuestion: Int
)
