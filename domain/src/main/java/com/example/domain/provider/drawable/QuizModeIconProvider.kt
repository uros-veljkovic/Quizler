package com.example.domain.provider.drawable

import com.example.domain.provider.AbstractResourceProvider
import com.example.domain.wrapper.IContextWrapper

class QuizModeIconProvider(context: IContextWrapper) : AbstractResourceProvider<Int>(context) {

    override val resourceTypeLowercase: String = "drawable"
    override val prefix = "ic_mode_"

    override fun getResource(resId: Int): Int = resId
}
