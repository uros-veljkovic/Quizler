package com.example.quizler.ui.screen.newquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.State
import com.example.domain.model.AnswerType
import com.example.domain.model.CreateNewQuestionAnswer
import com.example.domain.model.CreateNewQuestionBundle
import com.example.domain.usecase.ICreateNewQuestionUseCase
import com.example.quizler.InfoBannerDataMapper
import com.example.quizler.model.ChosableItem
import com.example.quizler.model.InfoBannerData
import com.example.quizler.ui.screen.score.ScoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateNewQuestionViewModel(
    private val choosableCategoryItemsProvider: ChoosableCategoryItemsProvider,
    private val createNewQuestionUseCase: ICreateNewQuestionUseCase,
    private val infoBannerDataMapper: InfoBannerDataMapper
) : ViewModel() {

    private val _screenState = MutableStateFlow(CreateNewQuestionScreenState())
    val screenState = _screenState.stateIn(
        viewModelScope, SharingStarted.Lazily, CreateNewQuestionScreenState()
    )

    private val _infoBanner: MutableSharedFlow<InfoBannerData?> = MutableSharedFlow()
    val infoBanner = _infoBanner.asSharedFlow()

    init {
        observeCategoryItems()
    }

    private fun observeCategoryItems() {
        viewModelScope.launch {
            choosableCategoryItemsProvider().collect { categories ->
                _screenState.update {
                    it.copyWithUpdatedCategories(categories)
                }
            }
        }
    }

    fun onQuestionUpdate(newText: String) {
        _screenState.update {
            it.copyWithUpdatedQuestion(newText, null)
        }
    }

    fun onAnswerUpdate(answerType: AnswerType, newText: String) {
        _screenState.update {
            it.copyWithUpdatedAnswer(answerType, newText)
        }
    }

    fun onCategoryChosen(chosableItem: ChosableItem.Content) {
        _screenState.update {
            it.copyWithChosenCategory(chosableItem)
        }
    }

    fun onCorrectAnswerChosen(answerType: AnswerType) {
        _screenState.update {
            it.copy(chosenCorrectAnswer = answerType)
        }
    }

    fun onSaveQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _screenState.value
            if (state.hasAnyEmptyField()) {
                showInfoBanner(InfoBannerData.InvalidQuestionFields)
                return@launch
            }
            val bundle = createDomainModel(_screenState.value)
            createNewQuestionUseCase(bundle).collect { createNewQuestionState ->
                when (createNewQuestionState) {
                    is State.Error -> {
                        switchLoading()
                        createNewQuestionState.error?.let { handleError(it) }
                        return@collect
                    }

                    is State.Loading -> switchLoading()
                    is State.Success -> {
                        switchLoading()
                        handleSuccess()
                        return@collect
                    }
                }
            }
        }
    }

    private fun createDomainModel(state: CreateNewQuestionScreenState): CreateNewQuestionBundle {
        return CreateNewQuestionBundle(
            question = state.question.text,
            categoryId = state.chosenCategory?.text ?: "",
            answers = state.answers.map {
                CreateNewQuestionAnswer(text = it.text, isCorrect = it.type == state.chosenCorrectAnswer)
            }
        )
    }

    private fun handleError(throwable: Throwable) {
        viewModelScope.launch {
            showInfoBanner(infoBannerDataMapper.map(throwable))
        }
    }

    private fun handleSuccess() {
        viewModelScope.launch {
            resetMainState()
            showInfoBanner(InfoBannerData.SuccessfullyCreatedNewQuestion)
        }
    }

    private suspend fun showInfoBanner(data: InfoBannerData) {
        _infoBanner.emit(data)
        delay(ScoreViewModel.DELAY_BEFORE_ERROR_DISAPPEAR)
        _infoBanner.emit(null)
    }

    private fun resetMainState() {
        _screenState.update {
            it.copyWithSuccessfulNewQuestionCreation()
        }
    }

    private fun switchLoading() {
        _screenState.update {
            it.copyWithSwitchedLoading()
        }
    }

    fun onExpandDropdown() {
        _screenState.update {
            it.copyWithCategoryDropdownStateAltered()
        }
    }

    fun onDeleteAll() {
        _screenState.update {
            it.copyWithResetedFields()
        }
    }
}
