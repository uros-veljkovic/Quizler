package com.example.data.local.entity

import androidx.room.Entity

@Entity(tableName = "reported_question", primaryKeys = ["questionId", "reportTypeId"])
data class InvalidQuestionReportEntity(
    val questionId: String,
    val reportTypeId: String
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
