package com.example.quizler.ui.screen.newquestion

import com.example.quizler.domain.model.AnswerType
import com.example.quizler.ui.components.fakeCategoryChips
import com.example.quizler.ui.model.DropdownItem

data class CreateNewQuestionScreenState(
    val question: QuestionFieldState = QuestionFieldState(),
    val answers: List<AnswerFieldState> = listOf(
        AnswerFieldState(AnswerType.A),
        AnswerFieldState(AnswerType.B),
        AnswerFieldState(AnswerType.C),
        AnswerFieldState(AnswerType.D),
    ),
    val chosenCorrectAnswer: AnswerType = AnswerType.A,
    val categories: List<DropdownItem.Content> = fakeCategoryChips,
    val isCategoriesDropdownExpanded: Boolean = false,
    val chosenCategory: DropdownItem.Content? = null,
) {
    fun copyWithUpdatedAnswer(
        type: AnswerType,
        newText: String,
        errorMessage: String? = null
    ): CreateNewQuestionScreenState {
        return this.copy(
            answers = answers.map {
                if (it.type == type) it.copy(
                    type = type,
                    text = newText,
                    errorMessage = errorMessage
                ) else it
            }
        )
    }

    fun copyWithUpdatedQuestion(text: String, errorMessage: String? = null): CreateNewQuestionScreenState {
        return copy(question = question.copy(text = text, errorMessage = errorMessage))
    }

    fun copyWithCategoryDropdownStateAltered(): CreateNewQuestionScreenState {
        return copy(isCategoriesDropdownExpanded = isCategoriesDropdownExpanded.not())
    }

    fun copyWithChosenCategory(dropdownItem: DropdownItem.Content): CreateNewQuestionScreenState {
        return copy(chosenCategory = dropdownItem, isCategoriesDropdownExpanded = isCategoriesDropdownExpanded.not())
    }
}
