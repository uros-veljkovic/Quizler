package com.example.quizler.ui.screen.quiz

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.quizler.domain.model.Answer
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.model.Difficulty
import com.example.quizler.domain.model.Question
import com.example.quizler.ui.components.quiz.QuestionNumberSpan
import com.example.quizler.ui.model.ReportType

data class QuizScreenState(
    val question: QuestionBundle? = null,
    val result: ResultInfo? = null,
    val questionNumberSpan: QuestionNumberSpan = QuestionNumberSpan(),
    val points: Int = 0,
    val isNoQuestionsGenerated: Boolean = false,
    val isExitDialogVisible: Boolean = false,
    private val isReportQuestionButtonVisible: Boolean = true,
    val isAnyAnswerChosen: Boolean? = null,
    val isReportQuestionDialogVisible: Boolean = false,
    val username: String = "",
    val shouldSaveUsername: Boolean = false,
    val shouldExitQuiz: Boolean = false,
    val reportTypes: List<ReportType> = emptyList(),
    val questionReportCount: Int = 0,
) {

    fun copyWithNewQuestion(questionBundle: QuestionBundle): QuizScreenState {
        return copy(
            question = questionBundle,
            isReportQuestionButtonVisible = true,
            isAnyAnswerChosen = null,
            questionNumberSpan = questionNumberSpan.copy(currentQuestion = questionNumberSpan.currentQuestion + 1)
        )
    }

    fun copyOnOneSecondLess(): QuizScreenState {
        return copy(question = question?.copyWithOneSecondLess())
    }

    fun copyNoAnswerProvidedOnTime(): QuizScreenState {
        return copy(isAnyAnswerChosen = false)
    }

    fun copyWithNoQuestionsGeneratedError(): QuizScreenState {
        return copy(isNoQuestionsGenerated = true)
    }

    fun copyWithTotalQuestionNumber(totalQuestionCount: Int): QuizScreenState {
        return copy(questionNumberSpan = questionNumberSpan.copy(totalQuestions = totalQuestionCount))
    }

    fun isInvalidQuestionReportButtonVisible() = questionReportCount <= 3 && isReportQuestionButtonVisible
}

@Immutable
@Stable
data class QuestionBundle(
    val time: Int,
    val question: Question,
    val answers: List<Answer>,
    val difficulty: Difficulty,
) {
    fun answeredWith(type: AnswerType): QuestionBundle {
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

    fun copyWithOneSecondLess(): QuestionBundle {
        return copy(time = time - 1)
    }
}

@Immutable
@Stable
data class ResultInfo(
    @DrawableRes val icon: Int,
    val title: String,
    val description: String,
)
