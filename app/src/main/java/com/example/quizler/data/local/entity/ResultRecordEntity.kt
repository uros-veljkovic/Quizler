package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_records")
data class ResultRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val points: Int,
    val modeId: String
)
