package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerRecordDto(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("isCorrect") val isCorrect: Boolean
)
