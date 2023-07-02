package com.example.quizler.ui.screen.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

interface IEmailManager {
    fun writeEmailTo(recipient: String, context: Context)
}

class EmailManager : IEmailManager {
    override fun writeEmailTo(recipient: String, context: Context) {
        val i = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, "Predlog za izmenu/poboljsanje Kvizler-a")
        }
        try {
            startActivity(context, Intent.createChooser(i, "Posalji email..."), null)
        } catch (e: Exception) {
        }
    }
}
