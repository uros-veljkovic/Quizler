package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quizler.domain.model.Difficulty

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val text: String,
    val difficulty: Difficulty,
    val categoryId: String,
)
