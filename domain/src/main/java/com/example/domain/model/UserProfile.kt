package com.example.domain.model

data class UserProfile(
    val username: String,
    val avatar: String,
    val profileImageUrl: String,
) {
    fun isPopulated() = username.isNotEmpty() && (avatar.isNotEmpty() || profileImageUrl.isNotEmpty())
}
