package com.example.quizler.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LengthModesDto(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("submodes") val submodes: List<LengthModeDto>,
)

data class LengthModeDto(
    @SerializedName("_id") val id: String,
    @SerializedName("parentModeId") val parentModeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("numberOfHints") val numberOfHints: Int = 3,
    @SerializedName("numberOfQuestions") val numberOfQuestions: Int = 20,
    @SerializedName("timePerQuestion") val timePerQuestion: Int = 15,
)
