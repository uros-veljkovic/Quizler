package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ScoreDto(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("score") val score: Int,
    @SerializedName("mode") val mode: String,
)
