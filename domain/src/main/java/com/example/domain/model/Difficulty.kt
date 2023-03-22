package com.example.domain.model

enum class Difficulty(val time: Int) {
    Easy(20), Medium(15), Hard(10);

    companion object {
        private const val MODE_ID_EASY = "61b5d3bd9323ff8bcc546351"
        private const val MODE_ID_MEDIUM = "61b5d3bd9323ff8bcc546352"
        private const val MODE_ID_HARD = "61b5d3bd9323ff8bcc546353"

        fun generateFrom(modeId: String): Difficulty? {
            return when (modeId) {
                MODE_ID_EASY -> Easy
                MODE_ID_MEDIUM -> Medium
                MODE_ID_HARD -> Hard
                else -> null
            }
        }
    }
}
