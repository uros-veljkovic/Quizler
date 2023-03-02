package com.example.quizler.ui.screen.newquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.data.remote.dto.CreateNewQuestionDto
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.usecase.CreateNewQuestionUseCase
import com.example.quizler.domain.usecase.GetChoosableCategoryItemsUseCase
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewQuestionViewModel @Inject constructor(
    private val getChoosableCategoryItemsUseCase: GetChoosableCategoryItemsUseCase,
    private val createNewQuestionUseCase: CreateNewQuestionUseCase,

    ) : ViewModel() {
    private val _screenState = MutableStateFlow(CreateNewQuestionScreenState())
    val screenState = _screenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CreateNewQuestionScreenState()
    )

    private val _infoBannerData: MutableSharedFlow<InfoBannerData?> = MutableSharedFlow()
    val infoBannerData = _infoBannerData.asSharedFlow()

    init {
        observeCategoryItems()
    }

    private fun observeCategoryItems() {
        viewModelScope.launch {
            getChoosableCategoryItemsUseCase().collect { categories ->
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
            createNewQuestionUseCase(_screenState.value).collect { createNewQuestionState ->
                when (createNewQuestionState) {
                    is State.Error -> {
                        switchLoading()
                        handleError()
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

    private fun handleError() {
        viewModelScope.launch {
            showInfoBanner(InfoBannerData.ErrorLoadingContent)
            resetMainState()
        }
    }

    private fun handleSuccess() {
        viewModelScope.launch {
            showInfoBanner(InfoBannerData.SuccessfullyCreatedNewQuestion)
            resetMainState()
        }
    }

    private suspend fun showInfoBanner(data: InfoBannerData) {
        _infoBannerData.emit(data)
        delay(ScoreViewModel.DELAY_BEFORE_ERROR_DISAPPEAR)
        _infoBannerData.emit(null)
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
        _screenState.update { it.copyWithCategoryDropdownStateAltered() }
    }

    fun onDeleteAll() {
        _screenState.update {
            CreateNewQuestionScreenState()
        }
    }
}

