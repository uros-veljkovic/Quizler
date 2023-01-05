package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestionDto(
    @SerializedName("_id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("countAnsweredCorrect") val countAnsweredCorrect: Int,
    @SerializedName("countAnsweredWrong") val countAnsweredWrong: Int,
    @SerializedName("answers") val answers: List<AnswerDto> = emptyList(),
    @SerializedName("isApproved") val isApproved: Boolean,
)
