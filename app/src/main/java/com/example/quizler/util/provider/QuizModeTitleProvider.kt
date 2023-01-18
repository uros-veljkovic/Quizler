package com.example.quizler.util.provider

import android.content.Context
import com.example.quizler.R
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QQuizModeTitleProvider

class QuizModeTitleProvider @Inject constructor(context: Context) : AbstractResourceProvider<String>(context) {

    override val resourceTypeLowercase: String = "string"
    override val prefix = "mode_title_"
    override val fallbackResourceId: Int = R.string.question_marks

    override fun getResource(resId: Int): String = context.getString(resId)
}
