package com.example.quizler.ui.screen.newquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.usecase.GetChoosableCategoryItemsUseCase
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.util.mapper.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateNewQuestionViewModel @Inject constructor(
    private val getChoosableCategoryItemsUseCase: GetChoosableCategoryItemsUseCase,
    private val mapper: DataMapper<CreateNewQuestionScreenState, CreateNewQuestionDto>
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
        println(mapper.map(_screenState.value).toString())
        viewModelScope.launch {
            _infoBannerData.emit(InfoBannerData.SuccessfullyCreatedNewQuestion)
            delay(ScoreViewModel.DELAY_BEFORE_ERROR_DISAPPEAR)
            _infoBannerData.emit(null)
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

class CreateNewQuestionDtoMapper : DataMapper<CreateNewQuestionScreenState, CreateNewQuestionDto> {
    override fun map(input: CreateNewQuestionScreenState): CreateNewQuestionDto {
        return CreateNewQuestionDto(
            text = input.question.text,
            categoryId = input.chosenCategory?.itemId ?: throw Exception("No category has been chosen !"),
            answers = input.answers.map {
                CreateNewQuestionAnswerDto(
                    text = it.text,
                    isCorrect = it.type == input.chosenCorrectAnswer
                )
            }
        )
    }
}

data class CreateNewQuestionDto(
    private val text: String,
    private val categoryId: String,
    private val answers: List<CreateNewQuestionAnswerDto>
) {
    override fun toString(): String {
        return """
            Text: $text
            Category ID: $categoryId
            Answers: ${
        answers.map { answer ->
            answer.text + ", Is correct: " + answer.isCorrect
        }
        }
        """.trimIndent()
    }
}

data class CreateNewQuestionAnswerDto(
    val text: String,
    val isCorrect: Boolean,
)
