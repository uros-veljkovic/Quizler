package com.example.quizler.domain.model

import androidx.core.util.rangeTo

enum class Difficulty(val time: Int) {
    Easy(20), Medium(15), Hard(10);

    companion object {
        private const val MODE_ID_EASY = "61b5d3bd9323ff8bcc546351"
        private const val MODE_ID_MEDIUM = "61b5d3bd9323ff8bcc546352"
        private const val MODE_ID_HARD = "61b5d3bd9323ff8bcc546353"

        private const val MIN_PERCENTAGE_FOR_HARD = 0.0
        private const val MAX_PERCENTAGE_FOR_HARD = 0.33
        private const val MIN_PERCENTAGE_FOR_MEDIUM = 0.33
        private const val MAX_PERCENTAGE_FOR_MEDIUM = 0.66

        fun generate(correct: Int, wrong: Int): Difficulty {
            return when (correct.toDouble() / wrong.toDouble()) {
                in MIN_PERCENTAGE_FOR_HARD rangeTo MAX_PERCENTAGE_FOR_HARD -> Hard
                in MIN_PERCENTAGE_FOR_MEDIUM rangeTo MAX_PERCENTAGE_FOR_MEDIUM -> Medium
                else -> Easy
            }
        }

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
