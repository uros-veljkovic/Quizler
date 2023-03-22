package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QuestionDto(
    @SerializedName("_id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("countAnsweredCorrect") val countAnsweredCorrect: Int,
    @SerializedName("countAnsweredWrong") val countAnsweredWrong: Int,
    @SerializedName("answers") val answers: List<AnswerDto> = emptyList(),
    @SerializedName("isApproved") val isApproved: Boolean,
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
