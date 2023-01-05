package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reported_question")
data class ReportedQuestionEntity(
    @PrimaryKey val id: String
)
