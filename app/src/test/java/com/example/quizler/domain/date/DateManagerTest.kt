package com.example.quizler.domain.date

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.Calendar

internal class DateManagerTest {

    private lateinit var sut: DateManager

    @Before
    fun setUp() {
        sut = DateManager()
    }

    @Test
    fun `test hasPast return true when 30 days have past`() {
        val now = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -31)
        }
        val hasPast30Days = sut.hasDaysPassed(now.timeInMillis, 30)
        assertTrue(hasPast30Days)
    }

    @Test
    fun `test hasPast return false when 29 days have past`() {
        val now = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -29)
        }
        val hasPast30Days = sut.hasDaysPassed(now.timeInMillis, 30)
        assertFalse(hasPast30Days)
    }
}
