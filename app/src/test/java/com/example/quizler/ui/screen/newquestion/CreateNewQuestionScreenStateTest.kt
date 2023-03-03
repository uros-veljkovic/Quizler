package com.example.quizler.ui.screen.newquestion

import com.example.quizler.domain.model.AnswerType
import junit.framework.TestCase.assertEquals
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
        val expectedTextAnswerA = "Ovo je novi tekst A"
        val expectedTextAnswerB = "Ovo je novi tekst B"
        val expectedTextAnswerC = "Ovo je novi tekst C"
        val expectedTextAnswerD = "Ovo je novi tekst D"
        state = state.copyWithUpdatedAnswer(AnswerType.A, expectedTextAnswerA)
            .copyWithUpdatedAnswer(AnswerType.B, expectedTextAnswerB)
            .copyWithUpdatedAnswer(AnswerType.C, expectedTextAnswerC)
            .copyWithUpdatedAnswer(AnswerType.D, expectedTextAnswerD)

        val actualTextAnswerA = state.answers.first { it.type == AnswerType.A }.text
        val actualTextAnswerB = state.answers.first { it.type == AnswerType.B }.text
        val actualTextAnswerC = state.answers.first { it.type == AnswerType.C }.text
        val actualTextAnswerD = state.answers.first { it.type == AnswerType.D }.text

        assertEquals(actualTextAnswerA, expectedTextAnswerA)
        assertEquals(actualTextAnswerB, expectedTextAnswerB)
        assertEquals(actualTextAnswerC, expectedTextAnswerC)
        assertEquals(actualTextAnswerD, expectedTextAnswerD)
    }

    @Test
    fun `copyWithUpdatedQuestion updates question text`() {
        val expected = "This is a new question text"
        state = state.copyWithUpdatedQuestion(expected)

        val actual = state.question.text

        assertEquals(expected, actual)
    }
}
