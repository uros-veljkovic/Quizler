package com.example.quizler.ui.screen.settings

import android.content.Context
import android.content.Intent
import android.net.Uri

interface IWebPageOpener {
    /**
     * Open a web page using the provided context and URL.
     *
     * @param context The context from which the web page will be opened.
     * @param url The URL of the web page to open.
     */
    fun openWebPage(context: Context, url: String)
}

class WebPageOpener : IWebPageOpener {
    override fun openWebPage(context: Context, url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}
