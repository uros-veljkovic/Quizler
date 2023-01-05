package com.example.quizler.data.local.entity

import androidx.room.Entity

@Entity(tableName = "difficulty", primaryKeys = ["id", "name"])
class DifficultyModeEntity(
    id: String,
    name: String,
    numberOfHints: Int,
    numberOfQuestions: Int,
    timePerQuestion: Int,
) : BaseQuizModeEntity(
    id = id,
    name = name,
    numberOfHints = numberOfHints,
    numberOfQuestions = numberOfQuestions,
    timePerQuestion = timePerQuestion,
)
