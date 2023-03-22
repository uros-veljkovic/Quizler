package com.example.quizler.ui.screen.home.mapper

import com.example.domain.model.mode.BaseMode
import com.example.domain.provider.AbstractResourceProvider
import com.example.quizler.model.QuizMode
import com.example.util.mapper.DataMapper

class QuizModeMapper(
    private val titleProvider: AbstractResourceProvider<String>,
    private val drawableProvider: AbstractResourceProvider<Int>,
) : DataMapper<BaseMode, QuizMode> {

    private val titleMap = mutableMapOf<String, Int>()
    private val iconMap = mutableMapOf<String, Int>()

    override fun map(input: BaseMode): QuizMode {
        var title = titleMap[input.name]
        if (title == null) {
            title = titleProvider.getResId(input.name)
            titleMap[input.name] = title
        }

        var icon = iconMap[input.name]
        if (icon == null) {
            icon = drawableProvider.getResId(input.name)
            iconMap[input.name] = icon
        }

        return QuizMode(
            id = input.id, title = title, icon = icon, questionNumber = input.numberOfQuestions.toString()
        )
    }
}
