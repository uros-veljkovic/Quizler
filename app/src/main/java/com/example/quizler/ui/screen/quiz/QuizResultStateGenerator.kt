package com.example.quizler.ui.screen.quiz

import android.content.Context
import com.example.quizler.R
import com.example.quizler.model.ResultInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class QuizResultStateGenerator(
    private val context: Context,
) : IQuizResultStateGenerator {

    private val session = MutableStateFlow(QuizSessionData())

    override fun getResultInfo(): ResultInfo {
        val sessionData = getSessionData()
        val description =
            context.getString(R.string.result, sessionData.totalCorrectAnswers, sessionData.totalNumberOfQuestions)
        return when (sessionData.totalCorrectAnswers.toDouble() / sessionData.totalNumberOfQuestions) {
            in 0.0..0.33 -> {
                ResultInfo(
                    icon = R.drawable.ic_crying,
                    title = context.getString(R.string.better_next_time),
                    description = description
                )
            }

            in 0.32..0.66 -> {
                ResultInfo(
                    icon = R.drawable.ic_neutral,
                    title = context.getString(R.string.not_bad),
                    description = description
                )
            }

            else -> {
                ResultInfo(
                    icon = R.drawable.ic_in_love,
                    title = context.getString(R.string.wow),
                    description = description
                )
            }
        }
    }

    override fun getSessionData(): QuizSessionData {
        return session.value
    }

    override fun answered(isCorrect: Boolean) {
        if (isCorrect) {
            session.update { it.copyOnQuestionAnsweredCorrect() }
        } else {
            session.update { it.copyOnQuestionAnsweredWrong() }
        }
    }

    override fun initSession(questionCount: Int) {
        session.update { it.copy(totalNumberOfQuestions = questionCount) }
    }

    override fun terminateCurrentSession() {
        session.update { QuizSessionData() }
    }
}
