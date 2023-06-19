package com.example.domain.model

data class UserProfile(
    val username: String,
    val avatar: String
) {
    fun isPopulated() = username.isNotEmpty() && avatar.isNotEmpty()
}
