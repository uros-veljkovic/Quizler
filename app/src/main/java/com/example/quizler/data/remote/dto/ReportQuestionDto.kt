package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReportQuestionDto(
    @SerializedName("reportTypeId") val reportTypeId: String,
    @SerializedName("questionId") val questionId: String
)
