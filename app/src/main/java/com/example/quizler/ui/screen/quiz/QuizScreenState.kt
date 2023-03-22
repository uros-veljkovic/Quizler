package com.example.quizler.ui.screen.quiz

import com.example.domain.model.QuestionWithAnswers
import com.example.domain.model.ReportType
import com.example.quizler.components.quiz.QuestionNumberSpan
import com.example.quizler.model.ResultInfo

data class QuizScreenState(
    val question: QuestionWithAnswers? = null,
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

    fun copyWithNewQuestion(questionWithAnswers: QuestionWithAnswers): QuizScreenState {
        return copy(
            question = questionWithAnswers,
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
