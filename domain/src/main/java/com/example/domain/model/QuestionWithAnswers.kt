package com.example.domain.model

data class QuestionWithAnswers(
    val time: Int,
    val question: Question,
    val answers: List<Answer>,
    val difficulty: Difficulty,
) {
    fun answeredWith(type: AnswerType): QuestionWithAnswers {
        return copy(
            answers = answers.map { answer ->
                if (answer.type == type)
                    answer.copy(isChosen = true)
                else
                    answer
            },
        )
    }

    fun hasAnsweredCorrectly(type: AnswerType): Boolean {
        return when (type) {
            AnswerType.A -> answers.getOrNull(0)?.isCorrect ?: false
            AnswerType.B -> answers.getOrNull(1)?.isCorrect ?: false
            AnswerType.C -> answers.getOrNull(2)?.isCorrect ?: false
            AnswerType.D -> answers.getOrNull(3)?.isCorrect ?: false
        }
    }

    fun copyWithOneSecondLess(): QuestionWithAnswers {
        return copy(time = time - 1)
    }
}
