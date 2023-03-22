package com.example.quizler.ui.screen.quiz.host

import com.example.domain.model.Difficulty
import com.example.domain.model.QuestionWithAnswers
import com.example.domain.usecase.IGetQuestionsUseCase
import com.example.quizler.model.Lengths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class QuizQuestionManager(
    private val getQuestionsUseCase: IGetQuestionsUseCase,
) : IQuizQuestionManager {

    override suspend fun getQuestions(
        modeId: String
    ): List<QuestionWithAnswers> {
        return withContext(Dispatchers.Default) {

            val questions = getQuestionsUseCase().first().shuffled()

            val lengthMode = Lengths.generateFrom(modeId)
            if (lengthMode != null) {
                return@withContext questions.takeLast(lengthMode.numberOfQuestions)
                    .map { it.copy(time = lengthMode.time) }
            }

            val difficulty = Difficulty.generateFrom(modeId)
            if (difficulty != null) {
                return@withContext getQuestionsFilteredByDifficulty(
                    questions,
                    difficulty
                ).map { it.copy(time = difficulty.time) }
            }

            getQuestionsFilteredByCategory(questions, modeId)
        }
    }

    override fun getTime(modeId: String): Int {
        val lengthMode = Lengths.generateFrom(modeId)
        if (lengthMode != null) return lengthMode.time

        val difficultyMode = Difficulty.generateFrom(modeId)
        if (difficultyMode != null) return difficultyMode.time

        return 20
    }

    private fun getQuestionsFilteredByDifficulty(
        questions: List<QuestionWithAnswers>,
        difficulty: Difficulty
    ): List<QuestionWithAnswers> {
        return if (questions.count { it.difficulty == difficulty } >= DEFAULT_NUMBER_OF_QUESTIONS)
            questions.filter {
                it.difficulty == difficulty
            }.take(
                DEFAULT_NUMBER_OF_QUESTIONS
            )
        else
            questions.take(DEFAULT_NUMBER_OF_QUESTIONS)
    }

    private fun getQuestionsFilteredByCategory(
        shuffledList: List<QuestionWithAnswers>,
        modeId: String
    ): List<QuestionWithAnswers> {
        return shuffledList.filter {
            it.question.categoryId == modeId
        }.take(
            DEFAULT_NUMBER_OF_QUESTIONS
        )
    }

    companion object {
        const val DEFAULT_NUMBER_OF_QUESTIONS = 20
    }
}
