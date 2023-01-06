package com.example.quizler.domain.date

interface IDateManager {
    /**
     * Checks if a certain number of [days] has past from [fromTimInMillis]
     *
     * @param days number of days passed
     * @param fromTimInMillis time in millis (in past)
     * @return true if certain number of days passed from [fromTimInMillis], otherwise false
     */
    fun hasPast(days: Int, fromTimInMillis: Long): Boolean
}
