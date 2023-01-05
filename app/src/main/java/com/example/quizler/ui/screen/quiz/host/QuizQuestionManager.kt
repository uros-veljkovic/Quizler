package com.example.quizler.ui.screen.quiz.host

import com.example.quizler.domain.model.Difficulty
import com.example.quizler.domain.model.Lengths
import com.example.quizler.domain.usecase.GetQuestionsUseCase
import com.example.quizler.ui.screen.quiz.QuestionBundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizQuestionManager @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : IQuizQuestionManager {

    override suspend fun getQuestions(
        modeId: String
    ): List<QuestionBundle> {
        return withContext(Dispatchers.Default) {

            val questions = getQuestionsUseCase().first().shuffled()

            val lengthMode = Lengths.generateFrom(modeId)
            if (lengthMode != null) {
                return@withContext questions.takeLast(lengthMode.numberOfQuestions).map { it.copy(time = lengthMode.time) }
            }

            val difficulty = Difficulty.generateFrom(modeId)
            if (difficulty != null) {
                return@withContext getQuestionsFilteredByDifficulty(questions, difficulty).map { it.copy(time = difficulty.time) }
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
        questions: List<QuestionBundle>,
        difficulty: Difficulty
    ): List<QuestionBundle> {
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
        shuffledList: List<QuestionBundle>,
        modeId: String
    ): List<QuestionBundle> {
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
