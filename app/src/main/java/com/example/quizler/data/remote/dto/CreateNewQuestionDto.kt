package com.example.quizler.data.remote.dto

data class CreateNewQuestionDto(
    private val text: String,
    private val categoryId: String,
    private val answers: List<CreateNewQuestionAnswerDto>
) {
    override fun toString(): String {
        return """
            Text: $text
            Category ID: $categoryId
            Answers: ${
        answers.map { answer ->
            answer.text + ", Is correct: " + answer.isCorrect
        }
        }
        """.trimIndent()
    }
}
