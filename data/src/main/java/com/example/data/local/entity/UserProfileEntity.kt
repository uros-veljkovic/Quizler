package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserProfileEntity(
    @PrimaryKey val userId: String,
    val id: String,
    val username: String?,
    val email: String?,
    val avatarName: String?,
    val profileImageUrl: String?,
)
