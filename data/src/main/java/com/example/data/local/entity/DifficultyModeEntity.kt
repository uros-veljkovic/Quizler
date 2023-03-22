package com.example.data.local.entity

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
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
