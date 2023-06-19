package com.example.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignInResponseDto(
    /**
     * Indicate if a user profile is fully created, meaning, user has profile with entered username
     * and chosen avatar.
     */
    @SerializedName("isProfileFullyCreated") val isProfileFullyCreated: Boolean
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
