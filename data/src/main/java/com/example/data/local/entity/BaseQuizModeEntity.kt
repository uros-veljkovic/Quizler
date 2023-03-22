package com.example.data.local.entity

open class BaseQuizModeEntity(
    val id: String,
    val name: String,
    val numberOfHints: Int,
    val numberOfQuestions: Int,
    val timePerQuestion: Int
) {
    override fun toString(): String {
        TODO("Create your own impl. since R8 throws error. See https://github.com/Kotlin/kotlinx.serialization/issues/2145")
    }
}
