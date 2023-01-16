package com.example.quizler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_type")
data class ReportTypeEntity(
    @PrimaryKey val id: String,
    val type: String,
)
