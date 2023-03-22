package com.example.domain.provider.string

import com.example.domain.provider.AbstractResourceProvider
import com.example.domain.wrapper.IContextWrapper

class QuizModeTitleProvider(context: IContextWrapper) : AbstractResourceProvider<String>(context) {

    override val resourceTypeLowercase: String = "string"
    override val prefix = "mode_title_"

    override fun getResource(resId: Int): String = context.getString(resId)
}
