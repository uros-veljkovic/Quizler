package com.example.quizler.utils.signin.manager.token.refresh

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent

class RefreshTokenWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val refreshTokenFacade: IRefreshTokenFacade
) : CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        return if (refreshTokenFacade.refreshToken())
            Result.success()
        else
            Result.failure()
    }
}
