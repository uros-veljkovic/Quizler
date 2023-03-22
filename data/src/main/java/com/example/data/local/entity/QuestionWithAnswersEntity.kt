package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithAnswersEntity(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId",
    ) val answers: List<AnswerEntity>,
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
