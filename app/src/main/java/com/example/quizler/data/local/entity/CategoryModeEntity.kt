package com.example.quizler.data.local.entity

import androidx.room.Entity

@Entity(tableName = "category", primaryKeys = ["id", "name"])
class CategoryModeEntity(
    id: String,
    name: String,
    numberOfHints: Int,
    numberOfQuestions: Int,
    timePerQuestion: Int,
) : BaseQuizModeEntity(
    id,
    name,
    numberOfHints,
    numberOfQuestions,
    timePerQuestion,
)
