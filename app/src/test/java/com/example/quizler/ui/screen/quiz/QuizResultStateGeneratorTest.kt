package com.example.quizler.ui.screen.quiz

import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

internal class QuizResultStateGeneratorTest {

    private lateinit var sut: QuizResultStateGenerator

    @Before
    fun setUp() {
        sut = QuizResultStateGenerator(mockk())
    }

    @Test
    fun givenFiveConsecutiveCorrectAnswer_whenGetessionDataMethod_thenTotalNumberOfPointsWillBe12() {
        repeat(5) {
            sut.answered(CORRECT)
        }
        val expected = 12
        val actual = sut.getSessionData().totalPoints
        assertEquals(expected, actual)
    }

    @Test
    fun givenThreeConsecutiveCorrectAnswerTwiceWithAWrongAnswerInMiddle_whenGetSessionDataMethod_thenTotalNumberOfPointsWillBe8() {
        repeat(3) {
            sut.answered(CORRECT)
        }
        sut.answered(INCORRECT)
        repeat(3) {
            sut.answered(CORRECT)
        }
        val expected = 8
        val actual = sut.getSessionData().totalPoints
        assertEquals(expected, actual)
    }

    @Test
    fun given20CorrectAnswers_whenGetSessionDataMethod_thenTotalNumberOfPointsWillBe17710() {
        repeat(20) {
            sut.answered(CORRECT)
        }
        val expected = 17710
        val actual = sut.getSessionData().totalPoints
        assertEquals(expected, actual)
    }

    @Test
    fun givenAMaximumOf5ConsecutiveAnswers_whenGetSessionDataMethodCalled_thenMaximumConsecutiveAnswersIs5() {
        repeat(5) {
            sut.answered(CORRECT)
        }
        sut.answered(INCORRECT)
        repeat(6) {
            sut.answered(CORRECT)
        }
        sut.answered(INCORRECT)
        repeat(3) {
            sut.answered(CORRECT)
        }
        val expected = 6
        val actual = sut.getSessionData().maxConsecutiveCorrectAnswers

        assertEquals(expected, actual)
    }

    @Test
    fun givenAnsweredTo20Questions_whenGetSessionDataMethodCalled_thenTotalAnswersIs20() {
        repeat(5) {
            sut.answered(CORRECT)
        }
        repeat(5) {
            sut.answered(INCORRECT)
        }
        repeat(5) {
            sut.answered(CORRECT)
        }
        repeat(5) {
            sut.answered(INCORRECT)
        }

        val expected = 10
        val actual = sut.getSessionData().totalCorrectAnswers
        assertEquals(expected, actual)
    }

    @Test
    fun givenAnswered5ConsecutiveAnswers_whenGetSessionDataMethodCalled_thenCurrentQuestionValueIsCorrect() {
        repeat(6) {
            sut.answered(CORRECT)
        }
        val expected = 13
        val actual = sut.getSessionData().currentQuestionValue
        assertEquals(expected, actual)
    }

    @Test
    fun givenAnswered5Question_whenGetSessionDataMethodCalled_thenTotalNumberOfQuestionsDidNotChange() {
        sut.initSession(20)
        repeat(6) {
            sut.answered(CORRECT)
        }
        val expected = 20
        val actual = sut.getSessionData().totalNumberOfQuestions
        assertEquals(expected, actual)
    }

    companion object {
        const val CORRECT = true
        const val INCORRECT = false
    }
}
