package com.example.quizler.util.provider

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.quizler.R
import javax.inject.Inject

class QuizModeIconProvider @Inject constructor(
    private val context: Context
) : AbstractResourceProvider<Drawable>(context) {

    override val resourceTypeLowercase: String = "drawable"
    override val prefix = "ic_mode_"
    override val fallbackResourceId: Int = R.drawable.app_logo

    override fun getResource(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)
}
