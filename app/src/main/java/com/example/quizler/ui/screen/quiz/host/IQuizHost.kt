package com.example.quizler.ui.screen.quiz.host

import com.example.quizler.domain.model.AnswerType
import com.example.quizler.ui.model.IChoosableOptionItem
import com.example.quizler.ui.screen.quiz.QuizScreenState
import com.example.quizler.ui.screen.quiz.QuizSessionData
import com.example.quizler.ui.screen.quiz.ResultInfo
import kotlinx.coroutines.flow.MutableStateFlow

interface IQuizHost {
    val state: MutableStateFlow<QuizScreenState>

    fun startQuiz(modeId: String)
    suspend fun answered(type: AnswerType): Boolean
    fun onReportQuestion()
    fun onBackPressed()
    fun exitQuiz()
    suspend fun getResultInfo(): ResultInfo

    fun setUsername(username: String)
    fun setSaveUsername(shouldSave: Boolean)
    fun getSessionData(): QuizSessionData
    fun confirmReportQuestion()
    fun onReportItemChosen(optionItem: IChoosableOptionItem)
}
