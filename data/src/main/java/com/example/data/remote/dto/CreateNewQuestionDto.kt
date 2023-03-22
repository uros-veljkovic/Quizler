package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateNewQuestionDto(
    @SerializedName("text") private val text: String,
    @SerializedName("categoryId") private val categoryId: String,
    @SerializedName("answers") private val answers: List<CreateNewQuestionAnswerDto>
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
