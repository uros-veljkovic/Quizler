package com.example.quizler.util.provider

import android.content.Context
import com.example.quizler.R
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QQuizModeDescriptionProvider

class QuizModeDescriptionProvider @Inject constructor(
    private val context: Context
) : AbstractResourceProvider<String>(context) {

    override val resourceTypeLowercase: String = "string"
    override val prefix = "mode_description_"
    override val postfix = ""
    override val isShouldCapitalizeResourceName = false
    override val fallbackResourceId: Int = R.string.question_marks

    override fun getResource(resId: Int): String = context.getString(resId)
}
