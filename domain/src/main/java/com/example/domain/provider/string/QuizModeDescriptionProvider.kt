package com.example.domain.provider.string

import com.example.domain.provider.AbstractResourceProvider
import com.example.domain.wrapper.IContextWrapper

class QuizModeDescriptionProvider(context: IContextWrapper) : AbstractResourceProvider<String>(context) {

    override val resourceTypeLowercase: String = "string"
    override val prefix = "mode_description_"
    override val postfix = ""
    override val isShouldCapitalizeResourceName = false

    override fun getResource(resId: Int): String = context.getString(resId)
}
