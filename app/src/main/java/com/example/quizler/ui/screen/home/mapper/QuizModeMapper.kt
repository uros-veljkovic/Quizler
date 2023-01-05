package com.example.quizler.ui.screen.home.mapper

import android.graphics.drawable.Drawable
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.util.mapper.DataMapper
import com.example.quizler.util.provider.AbstractResourceProvider
import com.example.quizler.util.provider.QQuizModeTitleProvider
import javax.inject.Inject

class QuizModeMapper @Inject constructor(
    @QQuizModeTitleProvider private val titleProvider: AbstractResourceProvider<String>,
    private val drawableProvider: AbstractResourceProvider<Drawable>,
) : DataMapper<BaseQuizModeEntity, QuizMode> {

    private val titleMap = mutableMapOf<String, Int>()
    private val iconMap = mutableMapOf<String, Int>()

    override fun map(input: BaseQuizModeEntity): QuizMode {
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
