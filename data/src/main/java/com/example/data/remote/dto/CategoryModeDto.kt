package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryModesDto(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("submodes") val submodes: List<CategoryModeDto>,
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}

@Keep
data class CategoryModeDto(
    @SerializedName("_id") val id: String,
    @SerializedName("parentModeId") val parentModeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("numberOfHints") val numberOfHints: Int = 3,
    @SerializedName("numberOfQuestions") val numberOfQuestions: Int = 20,
    @SerializedName("timePerQuestion") val timePerQuestion: Int = 15,
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
