package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @SerializedName("_id") val id: String,
    val isCorrect: Boolean,
    val text: String
)
