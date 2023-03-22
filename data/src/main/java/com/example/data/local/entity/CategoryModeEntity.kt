package com.example.data.local.entity

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
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
