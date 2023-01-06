package com.example.quizler.domain.date

import java.util.Date

class DateManager : IDateManager {

    override fun hasPast(days: Int, fromTimInMillis: Long): Boolean {
        return Date().time - Date(fromTimInMillis).time > days * 24 * 60 * 60 * 1000
    }
}
