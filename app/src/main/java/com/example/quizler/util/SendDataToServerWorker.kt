package com.example.quizler.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.domain.usecase.SendStoredDataToServerUseCase
import org.koin.core.Koin

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

class SendDataToServerWorkerFactory(
    private val koin: Koin,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val useCase = koin.get<SendStoredDataToServerUseCase>()
        return SendDataToServerWorker(appContext, workerParameters, useCase)
    }
}
