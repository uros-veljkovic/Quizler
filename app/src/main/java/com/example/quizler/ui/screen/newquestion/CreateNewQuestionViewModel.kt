package com.example.quizler.ui.screen.newquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.ui.model.DropdownItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateNewQuestionViewModel @Inject constructor() : ViewModel() {
    private val _screenState = MutableStateFlow(CreateNewQuestionScreenState())
    val screenState = _screenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CreateNewQuestionScreenState()
    )

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

    fun onCategoryChosen(dropdownItem: DropdownItem.Content) {
        _screenState.update {
            it.copyWithChosenCategory(dropdownItem)
        }
    }

    fun onCorrectAnswerChosen(answerType: AnswerType) {
        _screenState.update {
            it.copy(chosenCorrectAnswer = answerType)
        }
    }

    fun onSaveQuestion() {
        _screenState.update {
            CreateNewQuestionScreenState()
        }
    }

    fun onExpandDropdown() {
        _screenState.update { it.copyWithCategoryDropdownStateAltered() }
    }
}
