package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResultRecordDto(
    @SerializedName("username") val username: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("score") val score: Int
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
