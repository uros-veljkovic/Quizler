package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_records")
data class ResultRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val points: Int,
    val modeId: String
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
