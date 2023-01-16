package com.example.quizler.ui.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.model.ReportedQuestion
import com.example.quizler.domain.usecase.GetUsernameUseCase
import com.example.quizler.domain.usecase.ReportQuestionUseCase
import com.example.quizler.domain.usecase.SaveAnswerRecordUseCase
import com.example.quizler.domain.usecase.SaveResultRecordUseCase
import com.example.quizler.domain.usecase.SaveUsernameUseCase
import com.example.quizler.ui.screen.quiz.host.IQuizHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizHost: IQuizHost,
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val reportQuestionUseCase: ReportQuestionUseCase,
    private val saveAnswerRecordUseCase: SaveAnswerRecordUseCase,
    private val saveResultRecordUseCase: SaveResultRecordUseCase
) : ViewModel() {

    val state = quizHost.state
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
            quizHost.setUsername(getUsernameUseCase().first())
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
            reportQuestionUseCase(ReportedQuestion(questionId))
            quizHost.notifyQuestionReported()
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
}
