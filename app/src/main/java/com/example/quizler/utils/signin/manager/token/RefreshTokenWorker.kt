package com.example.quizler.utils.signin.manager.token

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.quizler.utils.signin.manager.GoogleSignInManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent

class RefreshTokenWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        val signInOptions = GoogleSignInManager.getSignInOptins()

        val googleSignInClient = GoogleSignIn.getClient(applicationContext, signInOptions)

        try {
            val account = googleSignInClient.silentSignIn().await()
            val idToken = account?.idToken
            if (idToken != null) {
                // Save the new ID token to DataStore
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}
