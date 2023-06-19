package com.example.quizler.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecase.SendStoredDataToServerUseCase

class SendDataToServerWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
    private val useCase: SendStoredDataToServerUseCase
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val isDataSentToServer = useCase.invoke()
        return if (isDataSentToServer) Result.success() else Result.failure()
    }
}
