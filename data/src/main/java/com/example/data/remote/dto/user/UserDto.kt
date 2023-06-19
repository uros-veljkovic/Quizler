package com.example.data.remote.dto.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDto(
    @SerializedName("_id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("profileImage") val profileImage: String? = null
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
