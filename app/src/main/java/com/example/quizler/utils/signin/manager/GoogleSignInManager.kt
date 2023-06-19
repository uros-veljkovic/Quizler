package com.example.quizler.utils.signin.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.quizler.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class GoogleSignInManager : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        val intent = GoogleSignIn.getClient(context, getSignInOptins())
        return intent.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> {
                GoogleSignIn.getSignedInAccountFromIntent(intent)
            }

            else -> null
        }
    }

    companion object {
        fun getSignInOptins() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestIdToken(BuildConfig.GOOGLE_SIGN_IN_CLIENT_ID)
            .requestEmail()
            .build()

        const val RequestCode = 12345
    }
}
