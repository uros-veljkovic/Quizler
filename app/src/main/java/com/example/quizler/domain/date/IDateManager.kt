package com.example.quizler.domain.date

interface IDateManager {
    /**
     * Checks if a certain number of [days] has past from [date]
     *
     * @param days number of days passed
     * @param date in past
     * @return true if certain number of days passed from [date], otherwise false
     */
    fun hasDaysPassed(dateInMillis: Long, days: Int): Boolean
}
