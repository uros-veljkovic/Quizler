package com.example.quizler.ui.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AnswerRecord
import com.example.domain.model.AnswerType
import com.example.domain.model.IChoosableOptionItem
import com.example.domain.model.ResultRecord
import com.example.domain.usecase.ISaveAnswerRecordUseCase
import com.example.domain.usecase.ISaveResultRecordUseCase
import com.example.domain.usecase.ISaveUsernameUseCase
import com.example.quizler.ui.screen.quiz.host.IQuizHost
import kotlinx.coroutines.launch

class QuizViewModel(
    private val quizHost: IQuizHost,
    private val saveUsernameUseCase: ISaveUsernameUseCase,
    private val saveAnswerRecordUseCase: ISaveAnswerRecordUseCase,
    private val saveResultRecordUseCase: ISaveResultRecordUseCase,
) : ViewModel() {

    val state = quizHost.state

    private var modeId: String = ""

    fun answered(type: AnswerType) {
        viewModelScope.launch {
            state.value.question?.question?.id?.let { questionId ->
                val isCorrectAnswer = quizHost.answered(type)
                saveAnswerRecordUseCase(AnswerRecord(questionId, isCorrectAnswer))
            }
        }
    }

    fun startQuiz(modeId: String) {
        this.modeId = modeId
        quizHost.startQuiz(modeId)
        setSavedUsername()
    }

    private fun setSavedUsername() {
        viewModelScope.launch {
            quizHost.setUsername(state.value.username)
        }
    }

    fun onBackPressed() {
        quizHost.onBackPressed()
    }

    fun onUsernameChange(value: String) {
        // TODO Change to real validation with regex
        if (value.contains(" ") || value.count() > 14) return
        quizHost.setUsername(value)
    }

    fun onSaveUsername(shouldSave: Boolean) {
        quizHost.setSaveUsername(shouldSave)
    }

    fun reportQuestion() {
        viewModelScope.launch {
            quizHost.onReportQuestion()
        }
    }

    fun exitQuiz() {
        viewModelScope.launch {
            quizHost.exitQuiz()
        }
    }

    fun endQuiz() {
        viewModelScope.launch {
            saveUsername()
            saveResult()
            quizHost.exitQuiz()
        }
    }

    private suspend fun saveResult() {
        val sessionData = quizHost.getSessionData()
        val username = state.value.username
        saveResultRecordUseCase(ResultRecord(username, modeId, sessionData.totalPoints))
    }

    private suspend fun saveUsername() {
        if (state.value.shouldSaveUsername) {
            saveUsernameUseCase(state.value.username)
        }
    }

    fun onReportItemChosen(optionItem: IChoosableOptionItem) {
        quizHost.onReportItemChosen(optionItem)
    }

    fun onConfirmReportQuestion() {
        quizHost.confirmReportQuestion()
    }
}
