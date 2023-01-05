package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer")
data class AnswerEntity(
    @PrimaryKey val answerId: String,
    val questionId: String,
    val text: String,
    val isCorrect: Boolean = false,
)
