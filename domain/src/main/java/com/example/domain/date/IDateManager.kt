package com.example.domain.date

interface IDateManager {
    /**
     * Checks if a certain number of [days] has past from [dateInMillis]
     *
     * @param days number of days passed
     * @param dateInMillis in past
     * @return true if certain number of days passed from [dateInMillis], otherwise false
     */
    fun hasDaysPassed(dateInMillis: Long, days: Int): Boolean
}
