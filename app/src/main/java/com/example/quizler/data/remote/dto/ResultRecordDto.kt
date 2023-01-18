package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultRecordDto(
    @SerializedName("username") val username: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("score") val score: Int
)
