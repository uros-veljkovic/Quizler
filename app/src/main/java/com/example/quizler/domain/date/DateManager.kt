package com.example.quizler.domain.date

import java.util.Calendar
import java.util.concurrent.TimeUnit

class DateManager : IDateManager {

    override fun hasDaysPassed(dateInMillis: Long, days: Int): Boolean {
        val currentDate = Calendar.getInstance()
        val diff = currentDate.timeInMillis - dateInMillis
        val passedDays = TimeUnit.MILLISECONDS.toDays(diff)
        return passedDays >= days
    }
}
