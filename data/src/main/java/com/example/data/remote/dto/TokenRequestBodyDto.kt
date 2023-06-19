package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TokenRequestBodyDto(
    @SerializedName("token") val token: String
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
