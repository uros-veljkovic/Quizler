package com.example.quizler.domain.model

enum class Lengths(val numberOfQuestions: Int, val time: Int) {
    Zen(20, 20),
    Exam(40, 20),
    Marathon(100, 20);

    companion object {
        private const val ZEN_MODE_ID = "61b5d40f9323ff8bcc546357"
        private const val EXAM_MODE_ID = "61b5d40f9323ff8bcc546358"
        private const val MARATHON_MODE_ID = "61b5d40f9323ff8bcc546359"

        fun generateFrom(modeId: String): Lengths? {
            return when (modeId) {
                ZEN_MODE_ID -> Zen
                EXAM_MODE_ID -> Exam
                MARATHON_MODE_ID -> Marathon
                else -> null
            }
        }
    }
}
