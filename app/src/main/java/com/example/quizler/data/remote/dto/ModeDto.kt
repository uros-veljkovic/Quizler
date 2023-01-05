package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ModeDto(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("numOfHints") val numberOfHints: Int = 3,
    @SerializedName("numOfQuestions") val numberOfQuestions: Int = 20,
    @SerializedName("timePerQuestion") val timePerQuestion: Int = 15,
)
