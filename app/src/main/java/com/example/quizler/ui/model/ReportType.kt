package com.example.quizler.ui.model

data class ReportType(
    private val id: String,
    private val title: String,
    private val isSelected: Boolean = false
) : IChoosableOptionItem {
    override fun getItemId() = id

    override fun getTitle() = title

    override fun getIsChosen() = isSelected
}
