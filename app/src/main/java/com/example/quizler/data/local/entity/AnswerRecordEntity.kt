package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_records")
data class AnswerRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: String,
    val isAnsweredCorrectly: Boolean,
)
