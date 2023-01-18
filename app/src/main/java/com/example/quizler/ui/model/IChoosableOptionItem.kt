package com.example.quizler.ui.model

interface IChoosableOptionItem {
    fun getItemId(): String
    fun getTitle(): String
    fun getIsChosen(): Boolean
}
