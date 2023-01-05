package com.example.quizler.data.local.entity

import androidx.room.Entity

@Entity(tableName = "length", primaryKeys = ["id", "name"])
class LengthModeEntity(
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
    timePerQuestion
)
