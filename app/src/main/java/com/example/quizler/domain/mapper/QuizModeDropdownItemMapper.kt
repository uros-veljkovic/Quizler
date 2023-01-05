package com.example.quizler.domain.mapper

import android.graphics.drawable.Drawable
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.util.mapper.DataMapper
import com.example.quizler.util.provider.AbstractResourceProvider
import com.example.quizler.util.provider.QQuizModeTitleProvider
import javax.inject.Inject

class QuizModeDropdownItemMapper @Inject constructor(
    private val iconProvider: AbstractResourceProvider<Drawable>,
    @QQuizModeTitleProvider
    private val titleProvider: AbstractResourceProvider<String>
) : DataMapper<BaseQuizModeEntity, DropdownItem.Content> {
    override fun map(input: BaseQuizModeEntity): DropdownItem.Content {
        return DropdownItem.Content(
            itemId = input.id,
            icon = iconProvider.getResId(input.name),
            text = titleProvider.getResourceByName(input.name) ?: ""
        )
    }
}
