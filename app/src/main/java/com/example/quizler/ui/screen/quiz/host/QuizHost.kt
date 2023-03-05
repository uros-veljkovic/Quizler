package com.example.quizler.ui.screen.quiz.host

import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.model.InvalidQuestionReport
import com.example.quizler.domain.usecase.GetReportTypesUseCase
import com.example.quizler.domain.usecase.GetUsernameUseCase
import com.example.quizler.domain.usecase.SendInvalidQuestionReportUseCase
import com.example.quizler.ui.model.IChoosableOptionItem
import com.example.quizler.ui.screen.quiz.IQuizResultStateGenerator
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.ui.screen.quiz.QuizScreenState
import com.example.quizler.ui.screen.quiz.QuizSessionData
import com.example.quizler.ui.screen.quiz.ResultInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import timber.log.Timber
import java.util.EmptyStackException
import java.util.Stack
import javax.inject.Inject

class QuizHost @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val resultGenerator: IQuizResultStateGenerator,
    private val questionFilterManager: IQuizQuestionManager,
    private val getResultTypesUseCase: GetReportTypesUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val sendInvalidQuestionReportUseCase: SendInvalidQuestionReportUseCase
) : IQuizHost {

    private var modeId: String = ""
    private var questionBundles = Stack<QuestionBundle>()

    private var timer: Job? = null

    override val state: MutableStateFlow<QuizScreenState> = MutableStateFlow(QuizScreenState())

    override fun startQuiz(modeId: String) {
        this.modeId = modeId
        coroutineScope.launch(Dispatchers.IO) {
            state.update { it.copy(reportTypes = getResultTypesUseCase().first(), username = getUsernameUseCase().first()) }
            populateQuestions(modeId)
            setNewQuestion()
            initTimer()
        }
    }

    private fun initTimer() {
        coroutineScope.launch {
            timer = launch {
                while (isActive) {
                    yield()
                    tickOrTimeOut(this)
                }
            }
        }
    }

    private suspend fun tickOrTimeOut(scope: CoroutineScope) {
        val allDialogsInvisible = state.value.isReportQuestionDialogVisible.not()
        if (allDialogsInvisible) {
            state.value.question?.time?.let { time ->
                delay(1000)
                if (time > 0) {
                    state.value = state.value.copyOnOneSecondLess()
                } else {
                    timeOut()
                    scope.cancel()
                }
            }
        }
    }

    private fun stopTimer() {
        coroutineScope.launch {
            timer?.cancelAndJoin()
            timer = null
        }
    }

    override suspend fun answered(type: AnswerType): Boolean {
        stopTimer()
        val isAnswerCorrect = setQuestionAnswered(type)
        delay(DELAY_BEFORE_QUESTION_CHANGE)
        setNewQuestion()
        initTimer()
        return isAnswerCorrect
    }

    override fun onBackPressed() {
        if (state.value.isReportQuestionDialogVisible) {
            hideReportDialog()
            return
        }
        state.update { it.copy(isExitDialogVisible = it.isExitDialogVisible.not()) }
    }

    private fun hideReportDialog() {
        state.update {
            it.copy(
                isReportQuestionDialogVisible = it.isReportQuestionDialogVisible.not(),
                reportTypes = it.reportTypes.map { it.copy(isSelected = false) }
            )
        }
    }

    private fun setQuestionAnswered(type: AnswerType): Boolean {
        return state.value.question?.let {
            val isAnswerCorrect = it.hasAnsweredCorrectly(type)
            resultGenerator.answered(isAnswerCorrect)
            val totalPoints = resultGenerator.getSessionData().totalPoints

            state.update { state ->
                state.copy(
                    question = state.question?.answeredWith(type),
                    isAnyAnswerChosen = true,
                    points = totalPoints
                )
            }
            return@let isAnswerCorrect
        } ?: false
    }

    private fun timeOut() {
        coroutineScope.launch {
            stopTimer()
            state.value = state.value.copyNoAnswerProvidedOnTime()
            delay(DELAY_BEFORE_QUESTION_CHANGE)
            setNewQuestion()
            initTimer()
        }
    }

    private suspend fun populateQuestions(modeId: String) {
        val questions = questionFilterManager.getQuestions(modeId)
        if (questions.isEmpty()) {
            state.update { it.copyWithNoQuestionsGeneratedError() }
            return
        }

        Timber.tag("QuizHost").d("Question count: ${questions.count()}")
        resultGenerator.initSession(questions.count())
        state.update { it.copyWithTotalQuestionNumber(questions.count()) }
        questionBundles.addAll(questions)
    }

    private fun setNewQuestion() {
        try {
            state.update { it.copyWithNewQuestion(questionBundles.pop()) }
        } catch (e: EmptyStackException) {
            stopTimer()
            showResult()
        }
    }

    override suspend fun getResultInfo(): ResultInfo {
        return resultGenerator.getResultInfo()
    }

    override fun onReportQuestion() {
        state.update {
            it.copy(
                isReportQuestionButtonVisible = false,
                isReportQuestionDialogVisible = true,
                questionReportCount = it.questionReportCount + 1
            )
        }
    }

    override fun confirmReportQuestion() {
        state.value.reportTypes.forEach { reportType ->
            if (reportType.getIsChosen()) {
                coroutineScope.launch(Dispatchers.IO) {
                    sendInvalidQuestionReportUseCase(
                        InvalidQuestionReport(
                            state.value.question?.question?.id ?: "",
                            reportType.getItemId()
                        )
                    )
                }
            }
        }
        state.update {
            it.copy(
                isReportQuestionDialogVisible = false,
                reportTypes = it.reportTypes.map { reportType -> reportType.copy(isSelected = false) }
            )
        }
    }

    override fun onReportItemChosen(optionItem: IChoosableOptionItem) {
        Timber.d("Types: " + state.value.reportTypes.toString())
        val updatedReportTypes = state.value.reportTypes.map {
            if (it.getItemId() == optionItem.getItemId())
                it.copy(isSelected = it.getIsChosen().not())
            else
                it
        }

        Timber.d("Types: $updatedReportTypes")

        state.update {
            it.copy(reportTypes = updatedReportTypes)
        }
    }

    override fun setUsername(username: String) {
        state.update { it.copy(username = username) }
    }

    override fun setSaveUsername(shouldSave: Boolean) {
        state.update { it.copy(shouldSaveUsername = shouldSave) }
    }

    override fun getSessionData(): QuizSessionData {
        return resultGenerator.getSessionData()
    }

    private fun showResult() {
        state.update { it.copy(result = resultGenerator.getResultInfo()) }
    }

    override fun exitQuiz() {
        stopTimer()
        questionBundles = Stack()
        resultGenerator.terminateCurrentSession()
        state.update { QuizScreenState(shouldExitQuiz = true) }
    }

    companion object {
        private const val DELAY_BEFORE_QUESTION_CHANGE = 3000L
    }
}
