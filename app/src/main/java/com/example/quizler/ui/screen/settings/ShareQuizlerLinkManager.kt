package com.example.quizler.ui.screen.settings

import android.content.Context
import androidx.core.app.ShareCompat

interface IShareQuizlerLinkManager {
    fun shareLink(context: Context)
}

class ShareQuizlerLinkManager() : IShareQuizlerLinkManager {
    override fun shareLink(context: Context) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setChooserTitle("Odaberi aplikaciju")
            .setText("Odigraj sa mnom partiju Kvizlera\n\nhttp://play.google.com/store/apps/details?id=com.wombatech.quizler${context.packageName}")
            .startChooser()
    }
}
