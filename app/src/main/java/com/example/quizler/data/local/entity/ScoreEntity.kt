package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class ScoreEntity(
    @PrimaryKey val _id: String,
    val username: String,
    val score: Int,
    val ranking: Int,
    val mode: String,
)
