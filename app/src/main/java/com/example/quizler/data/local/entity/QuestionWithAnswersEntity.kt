package com.example.quizler.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithAnswersEntity(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId",
    ) val answers: List<AnswerEntity>,
)
