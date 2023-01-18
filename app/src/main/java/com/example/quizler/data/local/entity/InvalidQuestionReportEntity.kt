package com.example.quizler.data.local.entity

import androidx.room.Entity

@Entity(tableName = "reported_question", primaryKeys = ["questionId", "reportTypeId"])
data class InvalidQuestionReportEntity(
    val questionId: String,
    val reportTypeId: String
)
