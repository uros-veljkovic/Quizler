package com.example.quizler.ui.screen.newquestion

import com.example.quizler.domain.model.AnswerType
import org.junit.Before
import org.junit.Test

class CreateNewQuestionScreenStateTest {

    private lateinit var state: CreateNewQuestionScreenState

    @Before
    fun setUp() {
        state = CreateNewQuestionScreenState()
    }

    @Test
    fun `copyWithUpdatedAnswer sets new text on answer`() {
        val expected = "Ovo je novi tekst"
        state = state.copyWithUpdatedAnswer(AnswerType.A, expected)
        val actual = state.answers.first { it.type == AnswerType.A }.text
        assert(actual == expected)
    }
}
