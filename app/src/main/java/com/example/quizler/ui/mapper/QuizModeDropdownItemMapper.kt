package com.example.quizler.ui.mapper

import com.example.domain.model.mode.BaseMode
import com.example.domain.provider.AbstractResourceProvider
import com.example.quizler.model.ChosableItem
import com.example.util.mapper.DataMapper

class QuizModeDropdownItemMapper(
    private val iconProvider: AbstractResourceProvider<Int>,
    private val titleProvider: AbstractResourceProvider<String>
) : DataMapper<BaseMode, ChosableItem.Content> {
    override fun map(input: BaseMode): ChosableItem.Content {
        return ChosableItem.Content(
            itemId = input.id,
            icon = iconProvider.getResourceByName(input.name),
            text = titleProvider.getResourceByName(input.name)
        )
    }
}
