package com.example.quizler.ui.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.usecase.GetUsernameUseCase
import com.example.quizler.domain.usecase.SaveAnswerRecordUseCase
import com.example.quizler.domain.usecase.SaveResultRecordUseCase
import com.example.quizler.domain.usecase.SaveUsernameUseCase
import com.example.quizler.ui.model.IChoosableOptionItem
import com.example.quizler.ui.screen.quiz.host.IQuizHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizHost: IQuizHost,
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val saveAnswerRecordUseCase: SaveAnswerRecordUseCase,
    private val saveResultRecordUseCase: SaveResultRecordUseCase,
    getUsernameUseCase: GetUsernameUseCase,
) : ViewModel() {

    val state = combine(quizHost.state, getUsernameUseCase()) { state, username ->
        state.copy(username = username)
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        QuizScreenState()
    )

    var modeId: String = ""

    fun answered(type: AnswerType) {
        viewModelScope.launch {
            state.value.question?.question?.id?.let { questionId ->
                val isCorrectAnswer = quizHost.answered(type)
                saveAnswerRecordUseCase(AnswerRecordDto(questionId, isCorrectAnswer))
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

    fun showExitDialog(show: Boolean) {
        quizHost.showExitDialog(show)
    }

    fun onUsernameChange(value: String) {
        // TODO Change to real validation with regex
        if (value.contains(" ") || value.count() > 14) return
        quizHost.setUsername(value)
    }

    fun onSaveUsername(shouldSave: Boolean) {
        quizHost.setSaveUsername(shouldSave)
    }

    fun reportQuestion(questionId: String) {
        viewModelScope.launch {
//            reportQuestionUseCase(ReportedQuestion(questionId))
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
        saveResultRecordUseCase(ResultRecordDto(username, modeId, sessionData.totalPoints))
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
