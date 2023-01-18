package com.example.quizler.util.provider

import android.content.Context
import com.example.quizler.R

class ReportTypeTitleResourceProvider(context: Context) : AbstractResourceProvider<String>(context) {

    override val resourceTypeLowercase: String = "string"
    override val prefix = "report_type_"
    override val fallbackResourceId: Int = R.string.question_marks

    override fun getResource(resId: Int): String = context.getString(resId)
}
