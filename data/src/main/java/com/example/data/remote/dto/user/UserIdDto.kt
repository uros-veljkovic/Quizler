package com.example.data.remote.dto.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserIdDto(
    @SerializedName("id") val id: String
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
