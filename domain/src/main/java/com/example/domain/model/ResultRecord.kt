package com.example.domain.model

data class ResultRecord(
    val username: String,
    val mode: String,
    val score: Int
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
