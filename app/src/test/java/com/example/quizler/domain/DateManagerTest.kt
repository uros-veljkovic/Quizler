package com.example.quizler.domain

import com.example.quizler.domain.date.DateManager
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

internal class DateManagerTest {

    private lateinit var sut: DateManager

    @Before
    fun setUp() {
        sut = DateManager()
    }

    @Test
    fun givenTimeInMillisFrom4DaysAgo_whenDaysIs5_thenHasPastReturnsFalse() {
        val day = ONE_SECOND * 60 * 60 * 24
        val date = Date().time.minus(4 * day)

        val expected = false
        val actual = sut.hasPast(5, date)

        assertEquals(expected, actual)
    }

    @Test
    fun givenTimeInMillisFrom5DaysAgo_whenDaysIs5_thenHasPastReturnsTrue() {
        val day = ONE_SECOND * 60 * 60 * 24
        val date = Date().time.minus(5 * day)

        val expected = true
        val actual = sut.hasPast(4, date)

        assertEquals(expected, actual)
    }

    companion object {
        const val ONE_SECOND = 1000
    }
}
