package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReportTypeDto(
    @SerializedName("_id") val id: String,
    @SerializedName("type") val type: String,
)
