package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @SerializedName("_id") val id: String,
    @SerializedName("isCorrect") val isCorrect: Boolean,
    @SerializedName("text") val text: String
)
