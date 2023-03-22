package com.example.domain.model

interface IChoosableOptionItem {
    fun getItemId(): String
    fun getTitle(): String
    fun getIsChosen(): Boolean
}
